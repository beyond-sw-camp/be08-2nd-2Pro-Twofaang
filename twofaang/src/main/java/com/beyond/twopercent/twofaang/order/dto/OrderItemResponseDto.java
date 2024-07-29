package com.beyond.twopercent.twofaang.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {

    private Long productId; // 상품 번호

    private int quantity; // 수량

    private int price; // 가격
}
