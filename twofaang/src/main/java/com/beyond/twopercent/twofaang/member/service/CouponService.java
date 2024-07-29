package com.beyond.twopercent.twofaang.member.service;


import com.beyond.twopercent.twofaang.member.dto.CouponRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Coupon;

import java.util.List;

public interface CouponService {
    // 모든 쿠폰
    List<Coupon> getAllCoupons();
    // 쿠폰 추가
    Coupon addCoupon(CouponRequestDto request, String category);
    // 쿠폰 수정
    Coupon updateCoupon(String couponCode, CouponRequestDto request, String category);
    // 쿠폰 삭제
    void deleteCoupon(Long couponId);
    // 사용 가능 쿠폰
    List<Coupon> getAllEnableCoupons();
    // 특정 쿠폰 조회
    Coupon getCouponByCode(String couponCode);
}
