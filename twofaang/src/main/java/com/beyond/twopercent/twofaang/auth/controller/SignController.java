package com.beyond.twopercent.twofaang.auth.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignController {

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

    @GetMapping("/user/change-password")
    public String showChangePasswordPage() {
        return "change-password"; // Thymeleaf 템플릿 파일 이름
    }

}
