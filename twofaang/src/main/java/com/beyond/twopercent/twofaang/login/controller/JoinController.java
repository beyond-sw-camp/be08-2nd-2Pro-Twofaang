package com.beyond.twopercent.twofaang.login.controller;

import com.beyond.twopercent.twofaang.login.dto.JoinDTO;
import com.beyond.twopercent.twofaang.login.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {

        String result = joinService.joinProcess(joinDTO);

        return result;
    }
}
