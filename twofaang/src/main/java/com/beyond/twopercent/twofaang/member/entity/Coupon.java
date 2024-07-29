package com.beyond.twopercent.twofaang.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_deadline")
    private LocalDate couponDeadline;

    @Column(name = "discount_rate", precision = 5, scale = 2)
    private int discountRate;

}