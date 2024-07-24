package com.beyond.twopercent.twofaang.auth.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.JoinDTO;
import com.beyond.twopercent.twofaang.auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/user/register")
    public String joinPage(){

        return "user/register";
    }


    @PostMapping("/join")
    public String joinProc(@ModelAttribute JoinDTO joinDto) {
        joinService.join(joinDto);
        return "ok";
    }
}