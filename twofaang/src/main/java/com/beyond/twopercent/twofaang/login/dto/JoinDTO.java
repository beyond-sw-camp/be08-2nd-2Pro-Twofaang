package com.beyond.twopercent.twofaang.login.dto;

import lombok.Data;

@Data
public class JoinDTO {

    private String email;
    private String password;
    private String name;
    private String mobile;
    private String role;
}
