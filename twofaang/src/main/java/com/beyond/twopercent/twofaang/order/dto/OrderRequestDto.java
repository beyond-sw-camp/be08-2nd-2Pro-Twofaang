package com.beyond.twopercent.twofaang.order.dto;

import com.beyond.twopercent.twofaang.order.entity.OrderState;
import com.beyond.twopercent.twofaang.order.entity.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderRequestDto {
    private Long memberId;

    private List<Long> productIds;

    private Long deliveryId;

    private String requestMsg;

    private LocalDateTime orderDate;

    private int totalPayment;

    private PaymentMethod paymentMethod;

    private int gradeDiscount;

    private int couponDiscount;

    private int realAmount;

    private OrderState orderState;
}
