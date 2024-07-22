package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.auth.repository.RefreshRepository;
import com.beyond.twopercent.twofaang.auth.service.RefreshTokenService;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    public MemberResponseDto getCurrentMemberInfo(HttpServletRequest request) {

        String refresh = refreshTokenService.checkRefresh(request);
        if (refresh == null) return null;

        String email = refreshRepository.findEmailByRefresh(refresh);

        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            return null;
        }

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .mobile(member.getMobile())
                .gradeId(member.getGradeId())
                .joinDate(member.getJoinDate())
                .role(member.getRole())
                .status(member.getStatus())
                .point(member.getPoint())
                .build();

        return memberResponseDto;
    }
}
