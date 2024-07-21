package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {
    MemberResponseDto getCurrentMemberInfo(HttpServletRequest request);
}
