package com.beyond.twopercent.twofaang.member.entity;

import com.beyond.twopercent.twofaang.member.entity.enums.Role;
import com.beyond.twopercent.twofaang.member.entity.enums.Status;

import com.beyond.twopercent.twofaang.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;


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
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "addr")
    private String addr;

    @Column(name = "addr_detail")
    private String addrDetail;

    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @CreationTimestamp
    @Column(name = "join_date", updatable = false)
    private Date joinDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_admin", nullable = false)
    private Role role = Role.ROLE_USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.Y;

    @Column(name = "point", nullable = false)
    private int point = 0; // 포인트

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders; // 주문 목록
}

