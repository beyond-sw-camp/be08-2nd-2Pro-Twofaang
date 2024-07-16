package com.beyond.twopercent.twofaang.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long memberId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;    // 이메일 (ID로 사용)

    @Column(name = "password", nullable = false)
    private String password; // 비밀번호

    @Column(name = "name", nullable = false)
    private String name;     // 이름

    @Column(name = "mobile")
    private String mobile;   // 전화번호

    @Column(name = "grade_id")
    private int gradeId;     // 등급 코드

    @CreationTimestamp
    @Column(name = "join_date", updatable = false)
    private Date joinDate;   // 가입일

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate; // 수정일

    @Enumerated(EnumType.STRING)
    @Column(name = "is_admin", nullable = false)
    private Role role = Role.ROLE_USER; // 권한

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.Y; // 상태

    @Column(name = "point", nullable = false)
    private int point = 0; // 포인트
}
