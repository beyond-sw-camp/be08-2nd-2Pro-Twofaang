package com.beyond.twopercent.twofaang.auth.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SignController {

    private final JoinService joinService;

    @GetMapping("/login")
    public String loginPage(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails != null) {
            return "redirect:/";  // 이미 로그인된 경우 메인 페이지로 리다이렉트
        }
        return "login";  // 타임리프 템플릿 이름
    }

    @GetMapping("/join")
    public String joinPage(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails != null) {
            return "redirect:/";  // 이미 로그인된 경우 메인 페이지로 리다이렉트
        }
        return "join";
    }

    @PostMapping("/join")
    @ResponseBody
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

    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "change-password"; // Thymeleaf 템플릿 파일 이름
    }

}
