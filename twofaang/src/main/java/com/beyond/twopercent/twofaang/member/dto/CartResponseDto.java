package com.beyond.twopercent.twofaang.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDto {
    private Long cartId;

    private Long productId;

    private String productName;

    private int quantity;

    private int price;

    private String productImage;
}
