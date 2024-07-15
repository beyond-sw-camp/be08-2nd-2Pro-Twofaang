package com.beyond.twopercent.twofaang.order.entity;

public enum OrderState {
    CREATED,        // 주문 생성됨
    PROCESSING,     // 처리 중
    SHIPPED,        // 배송됨
    DELIVERED,      // 배송 완료
    CANCELLED,      // 취소됨
    REFUNDED        // 환불됨
}
