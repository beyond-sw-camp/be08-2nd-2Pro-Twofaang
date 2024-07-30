package com.beyond.twopercent.twofaang.member.repository;

import com.beyond.twopercent.twofaang.member.entity.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);
}
