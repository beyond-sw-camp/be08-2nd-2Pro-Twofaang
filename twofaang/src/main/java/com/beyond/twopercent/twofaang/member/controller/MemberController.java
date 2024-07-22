package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/myinfo")
    public ResponseEntity<MemberResponseDto> myinfo(HttpServletRequest request) {
        MemberResponseDto member = memberService.getCurrentMemberInfo(request);
        return ResponseEntity.ok(member);
    }

}
