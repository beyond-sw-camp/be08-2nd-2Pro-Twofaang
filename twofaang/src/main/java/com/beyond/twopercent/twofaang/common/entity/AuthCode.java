package com.beyond.twopercent.twofaang.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_auth_code")
@Builder
public class AuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authcode_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;    // 요청 이메일

    @Column(name = "authcode", nullable = false, unique = true)
    private String authCode;    // 인증코드

    @Column(name = "expiration", nullable = false)
    private String expiration;
}
