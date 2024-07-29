package com.beyond.twopercent.twofaang.member.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_coupon_box")
public class CouponBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponbox_id")
    private Long couponBoxId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "is_used")
    private boolean isUsed;

    @Column(name = "expired_date")
    private LocalDate expiredDate;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

}
