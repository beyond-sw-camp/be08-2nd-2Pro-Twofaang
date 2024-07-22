package com.beyond.twopercent.twofaang.auth.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinDTO {

    private String email;
    private String password;
    private String name;
    private String mobile;
    private String role;
}
