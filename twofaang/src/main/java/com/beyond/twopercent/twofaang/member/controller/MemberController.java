package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.ModifyMemberRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 현재 로그인된 회원의 정보를 반환
    @GetMapping("/myinfo")
    public ResponseEntity<MemberResponseDto> getCurrentMemberInfo(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails
    ) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto memberResponseDto = memberService.getCurrentMemberInfo(email);
        return ResponseEntity.ok(memberResponseDto);
    }

    // 모든 회원 정보를 반환
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 회원 정보 업데이트
    @PutMapping("/update")
    public ResponseEntity<MemberResponseDto> updateMember(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
            @Valid @RequestBody ModifyMemberRequestDto requestDto
    ) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto updatedMember = memberService.updateMember(email, requestDto);
        return ResponseEntity.ok(updatedMember);
    }

    // 회원 포인트 업데이트
    @PutMapping("/points-update/{email}")
    public ResponseEntity<MemberResponseDto> updateMemberPoint(
            @PathVariable String email,
            @RequestParam int point
    ) {
        MemberResponseDto updatedMember = memberService.updateMemberPoint(email, point);
        return ResponseEntity.ok(updatedMember);
    }

    // 회원 상태 변경
    @PutMapping("/status/{email}")
    public ResponseEntity<MemberResponseDto> updateMemberStatus(
            @PathVariable String email,
            @RequestParam String status
    ) {
        MemberResponseDto updatedMember = memberService.updateMemberStatus(email, status);
        return ResponseEntity.ok(updatedMember);
    }
}
