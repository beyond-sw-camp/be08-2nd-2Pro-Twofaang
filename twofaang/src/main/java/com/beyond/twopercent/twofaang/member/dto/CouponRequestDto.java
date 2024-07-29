package com.beyond.twopercent.twofaang.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponRequestDto {
    private String couponName;
    private int discountRate;
    private LocalDate couponDeadline;
}
