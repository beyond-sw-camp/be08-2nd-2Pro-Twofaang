package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.entity.Coupon;
import com.beyond.twopercent.twofaang.member.entity.CouponBox;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.CouponBoxRepository;
import com.beyond.twopercent.twofaang.member.repository.CouponRepository;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponBoxServiceImpl implements CouponBoxService {

    private final MemberRepository memberRepository;
    private final CouponBoxRepository couponBoxRepository;
    private final CouponRepository couponRepository;

    @Override
    public List<CouponBox> getAllCouponsByMemberEmail(String email) {

        Optional<Member> member = memberRepository.findByEmail(email);

        List<CouponBox> allCouponBoxes = couponBoxRepository.findByMemberId(member.get().getMemberId());
        List<CouponBox> enCouponBoxes = new ArrayList<>();

        for(CouponBox couponBox : allCouponBoxes){
            if(couponBox.getExpiredDate().isAfter(LocalDate.now())){
                enCouponBoxes.add(couponBox);
            }
        }

        return enCouponBoxes;
    }

    @Override
    public void deleteCouponFromBox(Long couponBoxId) {
        couponBoxRepository.deleteById(couponBoxId);
    }

    @Override
    public boolean addCouponToBox(String couponCode, String email) {
        Optional<Coupon> optionalCoupon = Optional.ofNullable(couponRepository.findByCouponCode(couponCode));
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalCoupon.isEmpty() || optionalMember.isEmpty()) {
            return false;  // 쿠폰 또는 회원이 존재하지 않음
        }

        Coupon coupon = optionalCoupon.get();
        Member member = optionalMember.get();

        if (couponBoxRepository.findByMemberIdAndCouponCode(member.getMemberId(), couponCode)) {
            return false; // 이미 존재하는 쿠폰
        } else {
            CouponBox couponBox = CouponBox.builder()
                    .memberId(member.getMemberId())
                    .isUsed(false)
                    .expiredDate(coupon.getCouponDeadline())
                    .coupon(coupon)
                    .build();

            couponBoxRepository.save(couponBox);
            return true;
        }
    }
}
