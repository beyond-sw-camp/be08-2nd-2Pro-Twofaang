package com.beyond.twopercent.twofaang.order.service;

import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.order.dto.OrderItemRequestDto;
import com.beyond.twopercent.twofaang.order.dto.OrderItemResponseDto;
import com.beyond.twopercent.twofaang.order.dto.OrderRequestDto;
import com.beyond.twopercent.twofaang.order.dto.OrderResponseDto;
import com.beyond.twopercent.twofaang.order.entity.Order;


import com.beyond.twopercent.twofaang.order.entity.OrderItem;
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
import java.util.ArrayList;
import java.util.Collections;
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
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    // 특정 주문 조회
    @Override
    public OrderResponseDto findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        return convertToResponseDto(order);
    }

    @Override
    public Order findOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    // 회원의 주문 조회
    @Override
    public List<OrderResponseDto> findOrdersByMemberId(Long memberId) {
        List<Order> orders = orderRepository.findByMember_MemberId(memberId);
        return orders.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // 이메일로 회원의 주문 조회
    @Override
    public List<OrderResponseDto> findOrdersByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        List<Order> orders = orderRepository.findByMember_MemberId(member.getMemberId());

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

    @Override
    public OrderResponseDto createOrder(String email, String productIndexList, String productPriceList, String productAmountList, String productUrlFileList, String productNameList) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        // 파라미터를 처리하여 OrderItem 객체 리스트를 생성합니다.
        String[] productIndexes = productIndexList.split(",");
        String[] productPrices = productPriceList.split(",");
        String[] productAmounts = productAmountList.split(",");
        String[] productUrlFiles = productUrlFileList.split(",");
        String[] productNames = productNameList.split(",");

        List<OrderItem> orderItems = new ArrayList<>();
        int totalPayment = 0;

        for (int i = 0; i < productIndexes.length; i++) {
            final String productIndex = productIndexes[i];
            final String productPrice = productPrices[i];
            final String productAmount = productAmounts[i];

            Product product = productRepository.findById(Long.parseLong(productIndex))
                    .orElseThrow(() -> new ProductNotFoundException(Long.parseLong(productIndex)));

            int amount = Integer.parseInt(productAmount);
            int price = Integer.parseInt(productPrice);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(amount)
                    .price(price)
                    .build();

            orderItems.add(orderItem);
            totalPayment += price * amount;
        }

        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .orderDate(LocalDateTime.now())
                .totalPayment(totalPayment)
                .realPayment(totalPayment)
                .orderItems(orderItems)
                .build();

        // 주문과 주문 항목을 저장
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        orderRepository.save(order);

        return convertToResponseDto(order);
    }

    // DTO로 변환 후 반환
    private OrderResponseDto convertToResponseDto(Order order) {
        List<OrderItemResponseDto> orderItems = order.getOrderItems().stream()
                .map(orderItem -> OrderItemResponseDto.builder()
                        .productId(orderItem.getProduct().getProductId())
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .email(order.getMember().getEmail())
                .requestMsg(order.getRequestMsg())
                .orderDate(order.getOrderDate())
                .totalPayment(order.getTotalPayment())
                .paymentMethod(order.getPaymentMethod())
                .gradeDiscount(order.getGradeDiscount())
                .couponDiscount(order.getCouponDiscount())
                .realPayment((int) order.getRealPayment())
//                .orderState(order.getOrderState())
                .orderItems(orderItems) // 주문 항목 목록 설정
                .build();
    }

    // 회원의 주문 추가
    @Transactional
    @Override
    public OrderResponseDto addOrder(OrderRequestDto orderRequestDto) {
        // 회원 정보 조회
        Member member = memberRepository.findByEmail(orderRequestDto.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(orderRequestDto.getEmail()));

        // 주문 생성
        Order order = Order.builder()
                .member(member)
                .requestMsg(orderRequestDto.getRequestMsg())
                .orderDate(LocalDateTime.now())
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .gradeDiscount(orderRequestDto.getGradeDiscount())
                .couponDiscount(orderRequestDto.getCouponDiscount())
//                .orderState(OrderState.ORDERED)
                .build();

        // 상품 목록을 조회하고 주문에 추가 및 총액 계산
        int totalPayment = 0;
        for (OrderItemRequestDto itemRequest : orderRequestDto.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemRequest.getProductId()));

            // 재고 감소
            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new InsufficientStockException("상품 재고가 부족합니다.");
            }
            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            int itemTotalPrice = product.getPrice() * itemRequest.getQuantity();
            totalPayment += itemTotalPrice;

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(itemTotalPrice)
                    .build();
            order.getOrderItems().add(orderItem);
        }

        order.setTotalPayment(totalPayment);
        int realPayment = totalPayment - orderRequestDto.getGradeDiscount() - orderRequestDto.getCouponDiscount();
        order.setRealPayment(realPayment); // 등급, 쿠폰 할인율 반영된 가격

        // 주문 저장
        orderRepository.save(order);

        // DTO로 변환하여 반환
        return convertToResponseDto(order);
    }

    // 주문 상태 변경 메서드 추가
    @Override
    public void updateOrderState(Long orderId, OrderState orderState) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
//        order.setOrderState(orderState);
        orderRepository.save(order);
    }

//    // DTO로 변환 후 반환
//    private OrderResponseDto convertToResponseDto(Order order) {
//        List<OrderItemResponseDto> orderItems = order.getOrderItems().stream()
//                .map(orderItem -> OrderItemResponseDto.builder()
//                        .productId(orderItem.getProduct().getProductId())
//                        .quantity(orderItem.getQuantity())
//                        .price(orderItem.getPrice())
//                        .build())
//                .collect(Collectors.toList());
//
//        return OrderResponseDto.builder()
//                .email(order.getMember().getEmail())
//                .requestMsg(order.getRequestMsg())
//                .orderDate(order.getOrderDate())
//                .totalPayment(order.getTotalPayment())
//                .paymentMethod(order.getPaymentMethod())
//                .gradeDiscount(order.getGradeDiscount())
//                .couponDiscount(order.getCouponDiscount())
//                .realPayment(order.getRealPayment())
//                .orderState(order.getOrderState())
//                .orderItems(orderItems) // 주문 항목 목록 설정
//                .build();
//    }

    // OrderResponseDto를 사용하여 Order 객체를 업데이트하는 메서드
    private void updateOrderFromDto(Order order, OrderResponseDto orderResponseDto) {
        order.setRequestMsg(orderResponseDto.getRequestMsg());
        order.setOrderDate(orderResponseDto.getOrderDate());
        order.setTotalPayment(orderResponseDto.getTotalPayment());
        order.setPaymentMethod(orderResponseDto.getPaymentMethod());
        order.setGradeDiscount(orderResponseDto.getGradeDiscount());
        order.setCouponDiscount(orderResponseDto.getCouponDiscount());
        order.setRealPayment(orderResponseDto.getRealPayment());
//        order.setOrderState(orderResponseDto.getOrderState());

        // 주문 항목 업데이트
        List<OrderItem> updatedOrderItems = orderResponseDto.getOrderItems().stream()
                .map(orderItemDto -> {
                    Product product = productRepository.findById(orderItemDto.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(orderItemDto.getProductId()));

                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(orderItemDto.getQuantity())
                            .price(orderItemDto.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
        order.setOrderItems(updatedOrderItems);
    }
}