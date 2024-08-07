package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.Role;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.repository.GradeRepository;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderRepository orderRepository;
    private final GradeRepository gradeRepository;

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
    public void updateMember(MemberResponseDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.getEmail()).orElseThrow(() -> new RuntimeException("Member not found"));
        member.setName(memberDto.getName());
        member.setMobile(memberDto.getMobile());
        member.setAddr(memberDto.getAddr());
        member.setAddrDetail(memberDto.getAddrDetail());
        member.setZipcode(memberDto.getZipcode());
        memberRepository.save(member);  // 변경된 내용 저장
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

    @Override
    public String getPassword(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));
        return member.getPassword();
    }

    // 임시 비밀번호로 비밀번호 변경
    @Override
    public String SetTempPassword(String email, String tempPW) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

        try {
            member.setPassword(bCryptPasswordEncoder.encode(tempPW));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("상태 값 이상함");
        }

        member = memberRepository.save(member);
        return member.getName().toString() + "님의 임시 비밀번호가 발급되었습니다.\n" + "가입하신 이메일을 확인해주세요";
    }

    // 비밀번호 변경
    @Override
    public String updatePassword(String email, String tempPW) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원"));

        try {
            member.setPassword(bCryptPasswordEncoder.encode(tempPW));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("상태 값 이상함");
        }

        member = memberRepository.save(member);
        return member.getName() + "님의 비밀번호가 변경되었습니다.\n다시 로그인을 해주세요";
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
                .zipcode(member.getZipcode()) // 우편번호 추가
                .addr(member.getAddr()) // 주소 추가
                .addrDetail(member.getAddrDetail()) // 상세 주소 추가
                .build();
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?")              //매월 1일마다 모든 회원 등급 변경
//    @Scheduled(cron = "*/15 * * * * *")             // 15초마다 실행
    public void memberGradeUpdate(){
        List<Member> members = memberRepository.findAll();

        if(members.size() > 0){
            for(Member member : members){
                if(member.getRole().equals(Role.ROLE_USER)){
                    Integer totalPayment = orderRepository.totalPayment_MemberId((member.getMemberId()));
                    int actualPayment = (totalPayment != null) ? totalPayment : 0;

                    List<Grade> grades = gradeRepository.findGradesByMemberTotalPayment(actualPayment);

                    member.setGrade(grades.get(0));

                    memberRepository.save(member);
                }
            }
        }
    }

}
