package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.ModifyMemberRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {

    MemberResponseDto getCurrentMemberInfo(String email);

    List<MemberResponseDto> getAllMembers();

    MemberResponseDto updateMember(String email, ModifyMemberRequestDto memberRequestDto);

    MemberResponseDto updateMemberPoint(String email, int point);

    MemberResponseDto updateMemberStatus(String email, String status);

    MemberResponseDto getMemberByEmail(String email);

    void SetTempPassword(String to, String authNum);
}
