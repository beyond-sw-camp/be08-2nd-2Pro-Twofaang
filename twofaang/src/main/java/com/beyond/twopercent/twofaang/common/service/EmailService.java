package com.beyond.twopercent.twofaang.common.service;

import com.beyond.twopercent.twofaang.common.dto.AuthCodePostDto;
import com.beyond.twopercent.twofaang.common.dto.EmailMessage;
import com.beyond.twopercent.twofaang.common.entity.AuthCode;
import com.beyond.twopercent.twofaang.common.repository.AuthCodeRepository;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final AuthCodeRepository authCodeRepository;
    private final MemberService memberService;

    public List<AuthCode> getAllAuthCode() {
        return authCodeRepository.findAll();
    }

    // 임시 코드
    @Transactional
    public String sendMail(EmailMessage emailMessage, String type) {
        String authCode = createCode();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        if (type.equals("/common/password")) {
            memberService.SetTempPassword(emailMessage.getTo(), authCode);
        } else if (type.equals("/common/email")) {
            if (authCodeRepository.existsByEmail(emailMessage.getTo())) {
                authCodeRepository.deleteByEmail(emailMessage.getTo());
            }
            System.out.println("authCodeRepository.existsByEmail(emailMessage.getTo()) = " + authCodeRepository.existsByEmail(emailMessage.getTo()));
            AuthCode code = new AuthCode();
            AuthCodePostDto authCodePostDto = AuthCodePostDto.builder()
                    .email(emailMessage.getTo())
                    .authCode(authCode)
                    .expiration(new Date(System.currentTimeMillis() + 10 * 1000L).toString())
                    .build();
            code.setEmail(authCodePostDto.getEmail());
            code.setAuthCode(authCodePostDto.getAuthCode());
            code.setExpiration(authCodePostDto.getExpiration());
            authCodeRepository.save(code);
        }

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            mimeMessageHelper.setText(setContext(authCode, type), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
            log.info("Success");
            return authCode;
        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }

    // 인증번호 및 임시 비밀번호 생성 메서드
    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                default:
                    key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    // thymeleaf를 통한 html 적용
    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process(type, context);
    }
}