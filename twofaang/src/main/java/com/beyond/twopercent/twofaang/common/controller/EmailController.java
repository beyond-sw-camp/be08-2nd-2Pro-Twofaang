package com.beyond.twopercent.twofaang.common.controller;

import com.beyond.twopercent.twofaang.common.dto.EmailMessage;
import com.beyond.twopercent.twofaang.common.dto.EmailPostDto;
import com.beyond.twopercent.twofaang.common.dto.EmailResponseDto;
import com.beyond.twopercent.twofaang.common.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mails")
@RestController
@RequiredArgsConstructor
@Tag(name = "메일 서비스 APIs", description = "메일 서비스 Api 리스트")
public class EmailController {

    private final EmailService emailService;

    // 임시 비밀번호 발급
    @PostMapping("/password")
    @Operation(summary = "임시 비밀번호 발급", description = "임시 비밀번호를 발급하여 메일로 전송한다.")
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
    @Operation(summary = "인증 코드 발급", description = "인증 코드를 발급하여 메일로 전송한다.")
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