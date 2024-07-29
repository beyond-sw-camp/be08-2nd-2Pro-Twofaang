package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.entity.CouponBox;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CouponBoxService {
    List<CouponBox> getAllCouponsByMemberEmail(String email);

    void deleteCouponFromBox(Long couponBoxId);

    boolean addCouponToBox(String couponCode, String email);

}
