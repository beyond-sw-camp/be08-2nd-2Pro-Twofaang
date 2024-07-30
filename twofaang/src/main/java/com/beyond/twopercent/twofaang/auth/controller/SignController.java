package com.beyond.twopercent.twofaang.auth.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.auth.service.JoinService;
import com.beyond.twopercent.twofaang.auth.service.ReissueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Tag(name = "회원 인증 서비스 APIs", description = "로그인, 회원가입, 갱신같은 API 리스트")
public class SignController {

    private final JoinService joinService;
    private final ReissueService reissueService;

    @GetMapping("/login")
    @Operation(summary = "로그인 페이지", description = "로그인 페이지로 이동한다.")
    public String loginPage(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails != null) {
            return "redirect:/";  // 이미 로그인된 경우 메인 페이지로 리다이렉트
        }
        return "login";  // 타임리프 템플릿 이름
    }

    @GetMapping("/join")
    @Operation(summary = "회원 가입 페이지", description = "회원 가입 페이지로 이동한다.")
    public String joinPage(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails != null) {
            return "redirect:/";  // 이미 로그인된 경우 메인 페이지로 리다이렉트
        }
        return "join";
    }

    @GetMapping("/reset-password")
    @Operation(summary = "비밀번호 찾기 페이지", description = "비밀번호 찾기 페이지로 이동한다.")
    public String resetPasswordPage(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails != null) {
            return "redirect:/";  // 이미 로그인된 경우 메인 페이지로 리다이렉트
        }
        return "resetPassword";
    }

    @GetMapping("/change-password")
    @Operation(summary = "비밀번호 변경 페이지", description = "비밀번호 변경 페이지로 이동한다.")
    public String showChangePasswordPage() {
        return "change-password"; // Thymeleaf 템플릿 파일 이름
    }

    @PostMapping("/join")
    @ResponseBody
    @Operation(summary = "회원 가입", description = "회원 가입 기능")
    public ResponseEntity<?> join(@ModelAttribute JoinDTO joinDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            String joinResult = joinService.join(joinDto);
            response.put("success", true);
            response.put("username", joinDto.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping("/reissue")
    @ResponseBody
    @Operation(summary = "인증 갱신", description = "refresh 토큰을 재발급한다.")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return reissueService.reissue(request, response);
    }
}
