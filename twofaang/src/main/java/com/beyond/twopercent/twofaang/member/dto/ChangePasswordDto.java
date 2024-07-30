package com.beyond.twopercent.twofaang.member.dto;

import lombok.Getter;

@Getter
public class ChangePasswordDto {

    private String oldPassword;
    private String newPassword;

}
