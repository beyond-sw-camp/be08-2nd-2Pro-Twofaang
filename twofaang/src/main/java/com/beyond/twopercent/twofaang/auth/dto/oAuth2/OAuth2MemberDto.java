package com.beyond.twopercent.twofaang.auth.dto.oAuth2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OAuth2MemberDto {
    private String username;
    private String name;
    private String email;
    private String role;

    @Builder
    public OAuth2MemberDto(String username, String name, String email, String role) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
