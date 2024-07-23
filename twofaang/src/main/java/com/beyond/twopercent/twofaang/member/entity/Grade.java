package com.beyond.twopercent.twofaang.member.entity;

import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", nullable = false)
    private Long gradeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_name", nullable = false, unique = true)
    private GradeName gradeName;

    @Column(name = "target_amount", nullable = false)
    private int targetAmount;

    @Column(name = "discount_rate", nullable = false)
    private int discountRate;

    @OneToMany(mappedBy = "grade")
    private Set<Member> members; // 이 등급에 속한 회원들
}
