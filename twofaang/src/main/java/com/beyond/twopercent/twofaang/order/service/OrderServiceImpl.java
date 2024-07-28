package com.beyond.twopercent.twofaang.order.service;

import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.order.dto.OrderRequestDto;
import com.beyond.twopercent.twofaang.order.dto.OrderResponseDto;
import com.beyond.twopercent.twofaang.order.entity.Order;


import com.beyond.twopercent.twofaang.order.entity.OrderState;
import com.beyond.twopercent.twofaang.order.exception.InsufficientStockException;
import com.beyond.twopercent.twofaang.order.exception.MemberNotFoundException;
import com.beyond.twopercent.twofaang.order.exception.OrderNotFoundException;
import com.beyond.twopercent.twofaang.order.exception.ProductNotFoundException;
import com.beyond.twopercent.twofaang.order.repository.OrderRepository;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // 모든 주문 조회
    @Override
    public List<OrderResponseDto> findAllOrderDtos() {
        // Repository를 사용하여 모든 주문을 조회
        List<Order> orders = orderRepository.findAll();

        // 조회한 주문 목록을 OrderResponseDto로 변환하여 리스트로 반환
        return orders.stream()
                .map(this::convertToResponseDto) // 각 주문을 OrderResponseDto로 변환
                .collect(Collectors.toList());  // 변환된 OrderResponseDto들을 리스트로 수집
    }

    // 특정 주문 조회
    @Override
    public OrderResponseDto findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return convertToResponseDto(order);
    }

    // 회원의 주문 조회
    @Override
    public List<OrderResponseDto> findOrdersByMemberId(Long memberId) {
        List<Order> orders = orderRepository.findByMember_MemberId(memberId);

        return orders.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 회원의 주문 수정
    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderResponseDto orderResponseDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        updateOrderFromDto(order, orderResponseDto);
        orderRepository.save(order);

        return convertToResponseDto(order);
    }

    // 회원의 주문 추가
    @Transactional
    @Override
    public OrderResponseDto addOrder(OrderRequestDto orderRequestDto) {
        // 회원 정보 조회
        Member member = memberRepository.findById(orderRequestDto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(orderRequestDto.getMemberId()));

        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .delAddId(orderRequestDto.getDeliveryId())
                .requestMsg(orderRequestDto.getRequestMsg())
                .orderDate(LocalDateTime.now())
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .gradeDiscount(orderRequestDto.getGradeDiscount())
                .couponDiscount(orderRequestDto.getCouponDiscount())
                .realAmount(orderRequestDto.getRealAmount())
                .orderState(OrderState.ORDERED)
                .build();

        // 상품 목록을 조회하고 주문에 추가 및 총액 계산
        int totalPayment = 0;
        for (Long productId : orderRequestDto.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            // 재고 감소
            if (product.getQuantity() < 1) {
                throw new InsufficientStockException("상품 재고가 부족합니다.");
            }
            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);

            totalPayment += product.getPrice(); // 상품 가격 합산
            order.setProduct(product); // 주문에 상품 추가
        }

        order.setTotalPayment(totalPayment); // 총 주문 금액 설정

        // 주문 저장
        orderRepository.save(order);

        // DTO로 변환하여 반환
        return convertToResponseDto(order);
    }

    // 주문 상태 변경 메서드 추가
    @Override
    public void updateOrderState(Long orderId, OrderState orderState) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setOrderState(orderState);
        orderRepository.save(order);
    }

    // DTO로 변환 후 반환
    private OrderResponseDto convertToResponseDto(Order order) {
        return OrderResponseDto.builder()
                .memberId(order.getMember().getMemberId())
                .productId(order.getProduct().getProductId())
                .deliveryId(order.getDelAddId())
                .requestMsg(order.getRequestMsg())
                .orderDate(order.getOrderDate())
                .totalPayment(order.getTotalPayment())
                .paymentMethod(order.getPaymentMethod())
                .gradeDiscount(order.getGradeDiscount())
                .couponDiscount(order.getCouponDiscount())
                .realAmount(order.getRealAmount())
                .orderState(order.getOrderState())
                .count(1) // 주문 수량은 여기서 1로 설정
                .build();
    }

    // OrderResponseDto를 사용하여 Order 객체를 업데이트
    private void updateOrderFromDto(Order order, OrderResponseDto orderResponseDto) {
        Product product = productRepository.findById(orderResponseDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(orderResponseDto.getProductId()));
        order.setProduct(product);
        order.setDelAddId(orderResponseDto.getDeliveryId());
        order.setRequestMsg(orderResponseDto.getRequestMsg());
        order.setOrderDate(orderResponseDto.getOrderDate());
        order.setTotalPayment(orderResponseDto.getTotalPayment());
        order.setPaymentMethod(orderResponseDto.getPaymentMethod());
        order.setGradeDiscount(orderResponseDto.getGradeDiscount());
        order.setCouponDiscount(orderResponseDto.getCouponDiscount());
        order.setRealAmount(orderResponseDto.getRealAmount());
        order.setOrderState(orderResponseDto.getOrderState());
    }
}