package com.beyond.twopercent.twofaang.common.controller;

import com.beyond.twopercent.twofaang.common.dto.EmailMessage;
import com.beyond.twopercent.twofaang.common.dto.EmailPostDto;
import com.beyond.twopercent.twofaang.common.dto.EmailResponseDto;
import com.beyond.twopercent.twofaang.common.service.EmailService;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mails")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final MemberService memberService;


	// 임시 비밀번호 발급 
    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[Twofanng]임시 비밀번호 발급")
                .build();

        emailService.sendMail(emailMessage, "/common/password");

        return ResponseEntity.ok().build();
    }

	// 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    public ResponseEntity sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("[Twofanng]이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessage, "/common/email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok(emailResponseDto);
    }
}