package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.CouponRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Coupon;
import com.beyond.twopercent.twofaang.member.repository.CouponRepository;
import com.beyond.twopercent.twofaang.member.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
public class CouponController {

    private final CouponService couponService;
    private final CouponRepository couponRepository;

    // 모든 쿠폰 리스트 조회
    @GetMapping("/list")
    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    // 쿠폰 아이디로 특정 쿠폰 조회
    @GetMapping("/{couponId}")
    public Optional<Coupon> getCoupon(@PathVariable Long couponId) {
        return couponRepository.findById(couponId);
    }

    // couponId로 특정 쿠폰 조회
    @PutMapping("/update/{couponId}")
    public Coupon updateCoupon(@PathVariable Long couponId, @RequestBody CouponRequestDto request) {
        return couponService.updateCoupon(couponId, request);
    }

    // 쿠폰 추가
    @PostMapping("/add")
    public Coupon addCoupon(@RequestBody CouponRequestDto request) {
        return couponService.addCoupon(request);
    }

    // couponId로 특정 쿠폰을 삭제
    @DeleteMapping("/delete/{couponId}")
    public void deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
    }




}
