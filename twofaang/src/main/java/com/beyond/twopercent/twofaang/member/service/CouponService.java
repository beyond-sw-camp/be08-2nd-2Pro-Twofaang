package com.beyond.twopercent.twofaang.member.service;


import com.beyond.twopercent.twofaang.member.dto.CouponRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupons();
    Coupon addCoupon(CouponRequestDto request);
    Coupon updateCoupon(Long couponId, CouponRequestDto request);
    void deleteCoupon(Long couponId);
}