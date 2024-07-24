package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.member.dto.ModifyMemberRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDto getCurrentMemberInfo(String email) {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));
            return convertToDto(member);
    }

    @Override
    public List<MemberResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponseDto updateMember(String email, ModifyMemberRequestDto requestDto) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회권"));

        // 회원 정보 업데이트
        member.setName(requestDto.getName());
        member.setMobile(requestDto.getMobile());

        // 저장 및 반환
        Member updatedMember = memberRepository.save(member);
        return convertToDto(updatedMember);
    }


    @Override
    public MemberResponseDto updateMemberPoint(String email, int newPoint) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

        member.setPoint(newPoint);
        member = memberRepository.save(member);

        return convertToDto(member);
    }

    // 회원 상태 변경 (활성화 : Y, 비활성화(탈퇴) : N)
    @Override
    public MemberResponseDto updateMemberStatus(String email, String newStatus) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

        try {
            Status status = Status.valueOf(newStatus);
            member.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("상태 값 이상함");
        }

        member = memberRepository.save(member);
        return convertToDto(member);
    }

    // DTO로 변환 후 반환
    private MemberResponseDto convertToDto(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .mobile(member.getMobile())
                .gradeName(member.getGrade().getGradeName())
                .joinDate(member.getJoinDate())
                .role(member.getRole())
                .status(member.getStatus())
                .point(member.getPoint())
                .build();
    }
}
