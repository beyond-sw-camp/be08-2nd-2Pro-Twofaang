package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.CouponRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Coupon;
import com.beyond.twopercent.twofaang.member.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    // 모든 쿠폰 반환
    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    // 특정 쿠폰 반환
    @Override
    public Coupon getCouponByCode(String couponCode){
        if(couponRepository.existsByCouponCode(couponCode)) {
            return couponRepository.findByCouponCode(couponCode);
        }

        return null;
    }

    // 쿠폰 추가
    @Override
    public Coupon addCoupon(CouponRequestDto request) {

        List<Coupon> coupons = couponRepository.findAll();

        String couponNumber = String.format("%03d", coupons.size() + 1);

        String code = "COUPON_" + couponNumber;

        Coupon coupon = Coupon.builder()
                .couponCode(code)
                .couponName(request.getCouponName())
                .discountRate(request.getDiscountRate())
                .couponDeadline(request.getCouponDeadline())
                .build();

        return couponRepository.save(coupon);
    }


    // 특정 쿠폰 수정
    @Override
    public Coupon updateCoupon(String couponCode, CouponRequestDto request){

        if(couponRepository.existsByCouponCode(couponCode)){
            Coupon existingCoupon = couponRepository.findByCouponCode(couponCode);

            if (request.getCouponName() != null){
                existingCoupon.setCouponName(request.getCouponName());
            }
            if (request.getDiscountRate() != 0){
                existingCoupon.setDiscountRate(request.getDiscountRate());
            }
            if(request.getCouponDeadline() != null){
                existingCoupon.setCouponDeadline(request.getCouponDeadline());
            }

            return couponRepository.save(existingCoupon);
        }

        return null;
    }


    // 쿠폰 삭제
    @Override
    public void deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
    }

    @Override
    public List<Coupon> getAllEnableCoupons(){
        List<Coupon> AllCoupons = couponRepository.findAll();
        List<Coupon> EnableCoupons = new ArrayList<>();

        for (Coupon coupon : AllCoupons) {
            if(coupon.getCouponDeadline().isAfter(LocalDate.now())){
                EnableCoupons.add(coupon);
            }
        }

        return EnableCoupons;
    }
}
