package com.beyond.twopercent.twofaang.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {

    private Long productId; // 상품 번호

    private String productName; // 상품 이름

    private int quantity; // 수량

    private int price; // 가격

    private String urlFilename;

    private LocalDateTime orderDate; // 주문 날짜 필드 추가
}
