package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.auth.service.JoinService;
import com.beyond.twopercent.twofaang.auth.service.ReissueService;
import com.beyond.twopercent.twofaang.member.dto.ModifyMemberRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
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
    @ResponseBody
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissue(request, response);
    }

    @GetMapping("/myinfo")
    public String myInfoPage(Model model, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto member = memberService.getMemberByEmail(email);
        MemberResponseDto memberResponseDto = memberService.getCurrentMemberInfo(email);
        model.addAttribute("member", memberResponseDto);
        return "/members/myinfo";  // 회원 정보 확인 페이지로 이동
    }


    @GetMapping("/edit")
    public String editMemberInfo(@AuthenticationPrincipal CustomMemberDetails customMemberDetails, Model model) {
        // 사용자 이메일을 가져와서 서비스 메서드 호출
        String email = customMemberDetails.getEmail();
        MemberResponseDto member = memberService.getMemberByEmail(email);
        if (member == null) {
            // 오류 처리 로직 추가
            throw new RuntimeException("Member not found");
        }
        model.addAttribute("detail", member);  // "detail"이라는 이름으로 모델에 추가
        return "members/edit";  // 뷰의 이름
    }

    // 회원 정보 수정 처리
    @PostMapping("/update")
    public String updateMemberInfo(@ModelAttribute("detail") MemberResponseDto member) {
        memberService.updateMember(member);
        return "redirect:/members/myinfo";  // 정보 페이지로 리다이렉트
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

    // 회원 탈퇴
    @PutMapping("/withdraw")
    @ResponseBody
    public ResponseEntity<MemberResponseDto> updateMemberStatus(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails
    ) {
        MemberResponseDto updatedMember = memberService.updateMemberStatus(customMemberDetails.getEmail(), String.valueOf(Status.N));
        return ResponseEntity.ok(updatedMember);
    }

}
