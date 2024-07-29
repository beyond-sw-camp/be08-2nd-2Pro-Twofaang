package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.entity.CouponBox;
import com.beyond.twopercent.twofaang.member.service.CouponBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/couponbox")
public class CouponBoxController {

    private final CouponBoxService couponBoxService;

    // 사용자가 가지고 있는 쿠폰 중, 기간이 지나지 않은 쿠폰만을 출력
    @GetMapping("/list")
    public String getAllCoupons(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<CouponBox> coupons = couponBoxService.getAllCouponsByMemberEmail(email);
        model.addAttribute("coupons", coupons);
        return "coupon/myCouponList";
    }

    // 사용자가 가지고 있는 쿠폰 을 자신의 쿠폰 박스에서 삭제
    @PostMapping("/delete")
    public String deleteCoupon(@RequestParam("couponBoxId") Long couponBoxId) {
        couponBoxService.deleteCouponFromBox(couponBoxId);

        return "redirect:/couponbox/list";
    }


    // 사용자가 가지고 있지않은 쿠폰만을 추가
    @PostMapping("/add")
    public ModelAndView addCoupon(@RequestParam("couponCode") String couponCode, Authentication authentication, ModelAndView modelAndView) {
        boolean success = couponBoxService.addCouponToBox(couponCode, authentication.getName());
        if(success) {
            modelAndView.addObject("msg", "쿠폰이 추가되었습니다.");
        } else {
            modelAndView.addObject("msg", "이미 추가한 쿠폰입니다.");
        }

        modelAndView.addObject("location", "/coupon/list");
        modelAndView.setViewName("common/msg");

        return modelAndView;
    }

}
