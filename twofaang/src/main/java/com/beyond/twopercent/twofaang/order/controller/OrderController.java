package com.beyond.twopercent.twofaang.order.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.member.entity.Coupon;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.CouponRepository;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.order.dto.OrderRequestDto;
import com.beyond.twopercent.twofaang.order.dto.OrderResponseDto;
import com.beyond.twopercent.twofaang.order.entity.Order;
import com.beyond.twopercent.twofaang.order.entity.OrderItem;
import com.beyond.twopercent.twofaang.order.entity.OrderState;
import com.beyond.twopercent.twofaang.order.exception.MemberNotFoundException;
import com.beyond.twopercent.twofaang.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

//    // 모든 주문 조회 (관리자)
//    @GetMapping("/admin/orders/list")
//    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
//        List<OrderResponseDto> orderDtos = orderService.findAllOrderDtos();
//        return ResponseEntity.ok(orderDtos);
//    }
//
//    // 특정 주문 조회 (관리자)
//    @GetMapping("/admin/orders/{order_id}")
//    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long order_id) {
//        OrderResponseDto orderDto = orderService.findOrderById(order_id);
//        return ResponseEntity.ok(orderDto);
//    }

    // 회원의 모든 주문 조회
    @GetMapping("/orders/list")
    public String getMemberOrders(Model model, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        String email = customMemberDetails.getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        List<OrderResponseDto> orderDtos = orderService.findOrdersByMemberId(member.getMemberId());
        model.addAttribute("orders", orderDtos);
        return "orders/orderList";
    }

    // 새로운 주문 추가
    @PostMapping("/user/order/result")
    public String createOrder(
            @RequestParam("productIndexList") String productIndexList,
            @RequestParam("productPriceList") String productPriceList,
            @RequestParam("productAmountList") String productAmountList,
            @RequestParam("productUrlFileList") String productUrlFileList,
            @RequestParam("productNameList") String productNameList,
            @RequestParam("finalPrice") int finalPrice,
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
            Model model) {

        String email = customMemberDetails.getEmail();
        OrderResponseDto order = orderService.createOrder(email, productIndexList, productPriceList, productAmountList, productUrlFileList, productNameList, finalPrice);

        // 각 주문 항목에 주문 날짜 추가
        LocalDateTime orderDate = order.getOrderDate();
        order.getOrderItems().forEach(item -> item.setOrderDate(orderDate));

        model.addAttribute("order", order);
        model.addAttribute("orderItems", order.getOrderItems()); // 주문 항목을 모델에 추가
        model.addAttribute("finalPrice", finalPrice);

        return "orders/result"; // 주문 확인 페이지
    }

//    // 회원의 모든 주문 조회
//    @GetMapping("/members/{member_id}/orders")
//    public ResponseEntity<List<OrderResponseDto>> getMemberOrders(@PathVariable Long member_id) {
//        List<OrderResponseDto> orderDtos = orderService.findOrdersByMemberId(member_id);
//        return ResponseEntity.ok(orderDtos);
//    }

//    // 회원의 주문 수정
//    @PutMapping("/members/{member_id}/orders/{order_id}")
//    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long member_id,
//                                                        @PathVariable Long order_id,
//                                                        @RequestBody OrderResponseDto orderResponseDto) {
//        OrderResponseDto orderDto = orderService.updateOrder(order_id, orderResponseDto);
//        return ResponseEntity.ok(orderDto);
//    }

//    @GetMapping("/result")
//    public String showOrderResult(Model model, @RequestParam("orderId") Long orderId) {
//        Order order = orderService.findOrderEntityById(orderId);
//        List<OrderItem> orderItems = order.getOrderItems();
//
//        model.addAttribute("orderItems", orderItems);
//        model.addAttribute("totalPayment", order.getTotalPayment());
//        model.addAttribute("orderName", order.getMember().getName());
//        model.addAttribute("message", order.getRequestMsg());
//
//        return "/orders/result";
//    }

//    // 새로운 주문 추가
//    @PostMapping("/members/{member_id}/orders")
//    public ResponseEntity<OrderResponseDto> addOrder(@PathVariable Long member_id,
//                                                     @RequestBody OrderRequestDto orderRequestDto) {
//        // 회원 ID를 OrderRequestDto에 설정
//        orderRequestDto.setEmail(email); // 회원 ID 설정
//
//        // OrderService를 통해 새로운 주문을 생성하고 DTO로 반환
//        OrderResponseDto orderDto = orderService.addOrder(orderRequestDto);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
//    }

//    // 새로운 주문 추가
//    @PostMapping("/orders")
//    public String addOrder(@RequestBody OrderRequestDto orderRequestDto, @AuthenticationPrincipal CustomMemberDetails customMemberDetails, Model model) {
//        String email = customMemberDetails.getUsername();
//
//        orderRequestDto.setEmail(email); // 이메일 설정
//        OrderResponseDto orderDto = orderService.addOrder(orderRequestDto);
//        model.addAttribute("order", orderDto);
//
//        return "orderDetail";
//    }

//    // 특정 주문 취소 (상태 변경)
//    @PutMapping("/members/{member_id}/orders/{order_id}/cancel")
//    public ResponseEntity<Void> cancelOrder(@PathVariable Long member_id,
//                                            @PathVariable Long order_id) {
//        orderService.updateOrderState(order_id, OrderState.CANCELLED);
//        return ResponseEntity.noContent().build();
//    }
}
