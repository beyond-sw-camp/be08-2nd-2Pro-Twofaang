package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.ModifyMemberRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {

    MemberResponseDto getCurrentMemberInfo(String email);

    List<MemberResponseDto> getAllMembers();

    void updateMember(MemberResponseDto memberDto);

    MemberResponseDto updateMemberPoint(String email, int point);

    MemberResponseDto updateMemberStatus(String email, String status);

    MemberResponseDto getMemberByEmail(String email);

    // 임시 비밀번호로 비밀번호 변경
    String SetTempPassword(String email, String tempPW);

    String updatePassword(String email, String tempPW);
}
