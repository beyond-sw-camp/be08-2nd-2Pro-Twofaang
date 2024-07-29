package com.beyond.twopercent.twofaang.member.repository;

import com.beyond.twopercent.twofaang.member.entity.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT COUNT(c) FROM Coupon c WHERE c.couponCode LIKE :prefix%")
    int countByCouponCodePrefix(@Param("prefix") String prefix);

    Coupon findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);
}