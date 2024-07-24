package com.beyond.twopercent.twofaang.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponRequestDto {
    private String couponCode;
    private String couponName;
    private int discountRate;
}
