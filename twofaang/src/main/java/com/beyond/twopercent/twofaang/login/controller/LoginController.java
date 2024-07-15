package com.beyond.twopercent.twofaang.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username, @RequestParam String password, Model model) {
        // 로그인 처리 로직 (예: 인증 요청 보내기)
        // 성공 시:
        // return "redirect:/home";
        // 실패 시:
        // model.addAttribute("error", "Invalid username or password");
        // return "login";
        return "login"; // 임시로 로그인 페이지 반환
    }
}
