package com.beyond.twopercent.twofaang.order.service;

import com.beyond.twopercent.twofaang.order.dto.OrderRequestDto;
import com.beyond.twopercent.twofaang.order.dto.OrderResponseDto;
import com.beyond.twopercent.twofaang.order.entity.Order;
import com.beyond.twopercent.twofaang.order.entity.OrderState;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> findAllOrderDtos(); // 모든 주문 조회

    OrderResponseDto findOrderById(Long orderId); // 특정 주문 조회

    List<OrderResponseDto> findOrdersByMemberId(Long memberId); // 회원의 주문 조회

    List<OrderResponseDto> findOrdersByEmail(String email); // 이메일로 주문 조회

    OrderResponseDto updateOrder(Long orderId, OrderResponseDto orderResponseDto); // 회원의 주문 수정

    OrderResponseDto addOrder(OrderRequestDto orderRequestDto); // 새로운 주문 추가

    void updateOrderState(Long orderId, OrderState orderState); // 주문 상태 변경

    OrderResponseDto createOrder(String email, String productIndexList, String productPriceList, String productAmountList, String productUrlFileList, String productNameList, int finalPrice, Long couponId);

    void saveOrder(Order order);

    Order findOrderEntityById(Long orderId); // 특정 주문 조회
}
