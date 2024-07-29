package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.CouponRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Coupon;
import com.beyond.twopercent.twofaang.member.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // 모든 쿠폰 리스트 조회
    @GetMapping("/admin/coupons/list")
    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }


    // 쿠폰 아이디로 특정 쿠폰 조회
    @GetMapping("/admin/coupons/{couponCode}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable String couponCode) {
        Coupon coupon = couponService.getCouponByCode(couponCode);

        return coupon != null ? ResponseEntity.ok(coupon) : ResponseEntity.notFound().build();
    }

    // couponId로 특정 쿠폰 수정
    @PutMapping("/admin/coupons/update/{couponCode}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable String couponCode, @RequestBody CouponRequestDto request, String category){
        Coupon coupon = couponService.updateCoupon(couponCode, request, category);

        return coupon != null ? ResponseEntity.ok(coupon) : ResponseEntity.notFound().build();
    }

    // 쿠폰 추가
    @PostMapping("/admin/coupons/add")
    public Coupon addCoupon(@RequestBody CouponRequestDto request, String category) {
        return couponService.addCoupon(request, category);
    }

    // couponId로 특정 쿠폰을 삭제
    @DeleteMapping("/admin/coupons/delete/{couponId}")
    public void deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
    }

    // 마감일 지난 쿠폰을 제거하고 출력
    @GetMapping("/coupon/list")
    public String getCoupons(Model model) {
        List<Coupon> coupons = couponService.getAllEnableCoupons();
        model.addAttribute("coupons", coupons);

        return "coupon/couponList";
    }




}
