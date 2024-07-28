package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.ModifyMemberRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

        // 회원 삭제
        memberRepository.delete(member);

        // 새로운 회원 객체 생성 및 정보 설정
        Member newMember = Member.builder()
                .email(email)
                .name(requestDto.getName())
                .mobile(requestDto.getMobile())
                .grade(member.getGrade()) // 기존 등급 정보 유지
                .joinDate(member.getJoinDate()) // 기존 가입일 유지
                .role(member.getRole()) // 기존 역할 정보 유지
                .status(member.getStatus()) // 기존 상태 유지
                .point(member.getPoint()) // 기존 포인트 유지
                .build();

        // 새로운 회원 저장
        Member savedMember = memberRepository.save(newMember);

        return convertToDto(savedMember);
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

    @Override
    public MemberResponseDto getMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

        return convertToDto(member);
    }

    // 임시 비밀번호로 비밀번호 변경
    @Override
    public void SetTempPassword(String email, String tempPW) {

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
