package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.auth.service.JoinService;
import com.beyond.twopercent.twofaang.auth.service.ReissueService;
import com.beyond.twopercent.twofaang.member.dto.ModifyMemberRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JoinService joinService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 타임리프 템플릿 이름
    }

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<?> join(@ModelAttribute JoinDTO joinDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            joinService.join(joinDto);
            response.put("success", true);
            response.put("username", joinDto.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    private final ReissueService reissueService;
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissue(request, response);
    }

    // 현재 로그인된 회원의 정보를 반환
    @GetMapping("/myinfo")
    @ResponseBody
    public ResponseEntity<MemberResponseDto> getCurrentMemberInfo(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails
    ) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto memberResponseDto = memberService.getCurrentMemberInfo(email);
        return ResponseEntity.ok(memberResponseDto);
    }

    // 모든 회원 정보를 반환
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 이메일로 회원 정보 반환
    @GetMapping("{email}")
    @ResponseBody
    public ResponseEntity<MemberResponseDto> getMemberByEmail(
        @PathVariable String email
    ) {
        MemberResponseDto members = memberService.getMemberByEmail(email);
        return ResponseEntity.ok(members);
    }

    // 회원 정보 업데이트
    @PutMapping("/update")
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<MemberResponseDto> updateMemberPoint(
            @PathVariable String email,
            @RequestParam int point
    ) {
        MemberResponseDto updatedMember = memberService.updateMemberPoint(email, point);
        return ResponseEntity.ok(updatedMember);
    }

    // 회원 상태 변경
    @PutMapping("/status/{email}")
    @ResponseBody
    public ResponseEntity<MemberResponseDto> updateMemberStatus(
            @PathVariable String email,
            @RequestParam String status
    ) {
        MemberResponseDto updatedMember = memberService.updateMemberStatus(email, status);
        return ResponseEntity.ok(updatedMember);
    }
}
