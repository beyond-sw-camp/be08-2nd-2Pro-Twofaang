package com.beyond.twopercent.twofaang.order.controller;

import com.beyond.twopercent.twofaang.order.dto.OrderRequestDto;
import com.beyond.twopercent.twofaang.order.dto.OrderResponseDto;
import com.beyond.twopercent.twofaang.order.entity.OrderState;
import com.beyond.twopercent.twofaang.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 모든 주문 조회 (관리자)
    @GetMapping("/admin/orders/list")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orderDtos = orderService.findAllOrderDtos();
        return ResponseEntity.ok(orderDtos);
    }

    // 특정 주문 조회 (관리자)
    @GetMapping("/admin/orders/{order_id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long order_id) {
        OrderResponseDto orderDto = orderService.findOrderById(order_id);
        return ResponseEntity.ok(orderDto);
    }

    // 회원의 모든 주문 조회
    @GetMapping("/members/{member_id}/orders")
    public ResponseEntity<List<OrderResponseDto>> getMemberOrders(@PathVariable Long member_id) {
        List<OrderResponseDto> orderDtos = orderService.findOrdersByMemberId(member_id);
        return ResponseEntity.ok(orderDtos);
    }

    // 회원의 주문 수정
    @PutMapping("/members/{member_id}/orders/{order_id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long member_id,
                                                        @PathVariable Long order_id,
                                                        @RequestBody OrderResponseDto orderResponseDto) {
        OrderResponseDto orderDto = orderService.updateOrder(order_id, orderResponseDto);
        return ResponseEntity.ok(orderDto);
    }

    // 새로운 주문 추가
    @PostMapping("/members/{member_id}/orders")
    public ResponseEntity<OrderResponseDto> addOrder(@PathVariable Long member_id,
                                                     @RequestBody OrderRequestDto orderRequestDto) {
        // 회원 ID를 OrderRequestDto에 설정
        orderRequestDto.setMemberId(member_id); // 회원 ID 설정

        // OrderService를 통해 새로운 주문을 생성하고 DTO로 반환
        OrderResponseDto orderDto = orderService.addOrder(orderRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    // 특정 주문 취소 (상태 변경)
    @PutMapping("/members/{member_id}/orders/{order_id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long member_id,
                                            @PathVariable Long order_id) {
        orderService.updateOrderState(order_id, OrderState.CANCELED);
        return ResponseEntity.noContent().build();
    }
}
