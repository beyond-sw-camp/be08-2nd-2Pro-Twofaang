package com.beyond.twopercent.twofaang.member.repository;

import com.beyond.twopercent.twofaang.member.entity.CouponBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponBoxRepository extends JpaRepository<CouponBox, Long> {

    List<CouponBox> findByMemberId(Long memberId);

    @Query("SELECT CASE WHEN COUNT(cb) > 0 THEN true ELSE false END FROM CouponBox cb WHERE cb.memberId = :memberId AND cb.coupon.couponCode = :couponCode")
    boolean findByMemberIdAndCouponCode(long memberId, String couponCode);

}
