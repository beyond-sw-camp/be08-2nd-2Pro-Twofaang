package com.beyond.twopercent.twofaang.common.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AuthCodePostDto {

    private String email;    // 요청 이메일
    private String authCode;    // 인증코드
    private String expiration;

}
