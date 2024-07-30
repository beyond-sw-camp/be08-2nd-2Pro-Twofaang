package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.CouponRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Coupon;
import com.beyond.twopercent.twofaang.member.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "쿠폰 API", description = "쿠폰 관련 API 목록")
public class CouponController {

    private final CouponService couponService;

    // 모든 쿠폰 리스트 조회
    @GetMapping("/admin/coupons/list")
    @ResponseBody
    @Operation(summary = "모든 쿠폰 조회", description = "관리자 계정에서 등록되어있는 모든 쿠폰을 조회")
    public List<Coupon> getAllCoupons() {
        return couponService.getAllCoupons();
    }


    // 쿠폰 아이디로 특정 쿠폰 조회
    @GetMapping("/admin/coupons/{couponCode}")
    @ResponseBody
    @Operation(summary = "쿠폰 코드로 특정 쿠폰 조회", description = "관리자 계정에서 쿠폰 코드를 입력받아 해당 쿠폰을 조회")
    public ResponseEntity<Coupon> getCouponByCouponCode(@PathVariable String couponCode) {
        Coupon coupon = couponService.getCouponByCode(couponCode);

        return coupon != null ? ResponseEntity.ok(coupon) : ResponseEntity.notFound().build();
    }

    // couponCode로 특정 쿠폰 수정
    @PutMapping("/admin/coupons/update/{couponCode}")
    @ResponseBody
    @Operation(summary = "쿠폰 정보 수정", description = "관리자 계정에서 쿠폰 코드를 입력받아 해당 쿠폰을 수정")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable String couponCode, @RequestBody CouponRequestDto request){
        Coupon coupon = couponService.updateCoupon(couponCode, request);

        return coupon != null ? ResponseEntity.ok(coupon) : ResponseEntity.notFound().build();
    }

    // 쿠폰 추가
    @PostMapping("/admin/coupons/add")
    @ResponseBody
    @Operation(summary = "쿠폰 추가", description = "관리자 계정에서 새로운 쿠폰을 등록")
    public Coupon addCoupon(@RequestBody CouponRequestDto request) {
        return couponService.addCoupon(request);
    }

    // couponId로 특정 쿠폰을 삭제
    @ResponseBody
    @DeleteMapping("/admin/coupons/delete/{couponId}")
    @Operation(summary = "특정 쿠폰 삭제", description = "관리자 계정에서 쿠폰 ID를 입력받아 해당 쿠폰을 삭제")
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
