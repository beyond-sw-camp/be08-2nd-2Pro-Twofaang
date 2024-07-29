package com.beyond.twopercent.twofaang.order.dto;

import com.beyond.twopercent.twofaang.order.entity.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequestDto {

    private Long memberId; // 회원 번호

    private Long deliveryId; // 배송지 번호

    private String requestMsg; // 배송 요청사항

    private List<OrderItemRequestDto> orderItems; // 주문 항목 목록

    private PaymentMethod paymentMethod; // 결제 수단

    private int gradeDiscount; // 등급 할인액

    private int couponDiscount; // 쿠폰 할인액

}
