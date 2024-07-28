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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final ReissueService reissueService;
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissue(request, response);
    }

    @GetMapping("/myinfo")
    public String myInfoPage(Model model, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto memberResponseDto = memberService.getCurrentMemberInfo(email);
        model.addAttribute("member", memberResponseDto);
        return "/members/myinfo";  // 회원 정보 확인 페이지로 이동
    }

    @GetMapping("/edit")
    public String editPage(Model model, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto memberResponseDto = memberService.getCurrentMemberInfo(email);
        model.addAttribute("member", memberResponseDto);
        return "/members/edit";  // 회원 정보 수정 페이지로 이동
    }

    // 회원 정보 업데이트
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<MemberResponseDto> updateMember(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
            @Valid @RequestBody ModifyMemberRequestDto requestDto
    ) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto updatedMember = memberService.updateMember(email, requestDto);
        return ResponseEntity.ok(updatedMember);
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
