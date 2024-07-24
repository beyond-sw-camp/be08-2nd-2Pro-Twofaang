package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.CouponRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Coupon;
import com.beyond.twopercent.twofaang.member.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon addCoupon(CouponRequestDto request) {
        Coupon coupon = Coupon.builder()
                .couponCode(request.getCouponCode())
                .couponName(request.getCouponName())
                .discountRate(request.getDiscountRate())
                .build();
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Long couponId, CouponRequestDto request){
        Coupon existingCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id " + couponId));

        if (request.getCouponCode() != null){
            existingCoupon.setCouponCode(request.getCouponCode());
        }
        if (request.getCouponName() != null){
            existingCoupon.setCouponName(request.getCouponName());
        }
        if (request.getDiscountRate() != 0){
            existingCoupon.setDiscountRate(request.getDiscountRate());
        }

        return couponRepository.save(existingCoupon);
    }


    @Override
    public void deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
    }
}
