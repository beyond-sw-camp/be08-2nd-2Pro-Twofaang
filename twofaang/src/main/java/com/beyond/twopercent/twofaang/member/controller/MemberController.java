package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.auth.service.ReissueService;
import com.beyond.twopercent.twofaang.member.dto.ChangePasswordDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ReissueService reissueService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/change-password")
    public String changePasswordPage(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if(customMemberDetails != null)
            return "/changePassword";
        return "/login";
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

    @PostMapping("/change-password")
    @ResponseBody
    public String changePassword(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            @ModelAttribute ChangePasswordDto passwordDto) {
        try {
            // 현재 사용자의 이메일과 암호화된 비밀번호를 가져옴
            String email = customMemberDetails.getEmail();
            String encryptedPassword = customMemberDetails.getPassword();

            // 기존 비밀번호와 매치하는지 확인
            if (!bCryptPasswordEncoder.matches(passwordDto.getOldPassword(), encryptedPassword)) {
                redirectAttributes.addFlashAttribute("errorMessage", "기존 비밀번호가 일치하지 않습니다.");
                return "redirect:/changePassword";
            }

            // 새 비밀번호가 기존 비밀번호와 동일하지 않은지 확인
            if (bCryptPasswordEncoder.matches(passwordDto.getNewPassword(), encryptedPassword)) {
                redirectAttributes.addFlashAttribute("errorMessage", "새 비밀번호는 기존 비밀번호와 달라야 합니다.");
                return "redirect:/changePassword";
            }

            // 비밀번호 업데이트
            memberService.updatePassword(email, passwordDto.getNewPassword());

            // 로그아웃 처리 및 쿠키 삭제
            new SecurityContextLogoutHandler().logout(request, response, null);
            removeCookies(request, response);

            // 로그아웃 후 메시지 전달
            redirectAttributes.addFlashAttribute("message", "비밀번호 변경이 완료되었습니다.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 변경에 실패했습니다.");
            return "redirect:/changePassword";
        }
    }


    @PostMapping("/withdraw")
    public String withdrawMember(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        try {
            // 회원 상태 변경
            memberService.updateMemberStatus(customMemberDetails.getEmail(), String.valueOf(Status.N));

            // 로그아웃 처리 및 쿠키 삭제
            new SecurityContextLogoutHandler().logout(request, response, null);
            removeCookies(request, response);

            // 로그아웃 후 메시지 전달
            redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 완료되었습니다.");
            return "redirect:/logout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "회원 탈퇴에 실패했습니다.");
            return "redirect:/error";
        }
    }

    private void removeCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue(null);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }


}
