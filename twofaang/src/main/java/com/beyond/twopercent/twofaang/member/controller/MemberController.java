package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.auth.service.ReissueService;
import com.beyond.twopercent.twofaang.member.dto.ChangePasswordDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "회원 서비스 APIs", description = "회원 서비스 API 리스트")
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/myinfo")
    @Operation(summary = "내 정보 페이지", description = "내 정보 페이지로 이동한다.")
    public String myInfoPage(Model model, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        String email = customMemberDetails.getEmail();
        MemberResponseDto memberResponseDto = memberService.getCurrentMemberInfo(email);
        model.addAttribute("member", memberResponseDto);
        return "/members/myinfo";  // 회원 정보 확인 페이지로 이동
    }

    @GetMapping("/change-password")
    @Operation(summary = "비밀번호 변경 페이지", description = "비밀번호 변경 페이지로 이동한다.")
    public String changePasswordPage(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if(customMemberDetails != null)
            return "/changePassword";
        return "/login";
    }


    @GetMapping("/edit")
    @Operation(summary = "회원 수정 페이지", description = "회원 수정 페이지로 이동한다.")
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
    @Operation(summary = "회원 수정 기능", description = "회원 정보를 수정한다.")
    public String updateMemberInfo(@ModelAttribute("detail") MemberResponseDto member) {
        memberService.updateMember(member);
        return "redirect:/members/myinfo";  // 정보 페이지로 리다이렉트
    }

    @PostMapping("/change-password")
    @Operation(summary = "비밀번호 변경 기능", description = "비밀번호를 변경한다.")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody ChangePasswordDto passwordDto) {
        try {
            String email = customMemberDetails.getEmail();
            String encryptedPassword = memberService.getPassword(email);

            if (!bCryptPasswordEncoder.matches(passwordDto.getOldPassword(), encryptedPassword)) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "기존 비밀번호가 일치하지 않습니다."));
            }

            if (bCryptPasswordEncoder.matches(passwordDto.getNewPassword(), encryptedPassword)) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "새 비밀번호는 기존 비밀번호와 달라야 합니다."));
            }

            String updateMsg = memberService.updatePassword(email, passwordDto.getNewPassword());

            new SecurityContextLogoutHandler().logout(request, response, null);
            removeCookies(request, response);

            return ResponseEntity.ok(Map.of("success", true, "message", updateMsg));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "비밀번호 변경에 실패했습니다."));
        }
    }



    @PostMapping("/withdraw")
    @Operation(summary = "회원 탈퇴 기능", description = "회원 탈퇴를 한다.")
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
