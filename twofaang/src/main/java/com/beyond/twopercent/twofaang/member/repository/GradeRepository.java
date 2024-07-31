package com.beyond.twopercent.twofaang.member.repository;

import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    // GradeName으로 Grade를 찾기 위한 메소드 추가
    Grade findByGradeName(GradeName gradeName);

    // 회원의 조건에 맞는 Grade 조회
    @Query("select g from Grade g where g.targetAmount <= :totalPayment order by g.targetAmount desc")
    List<Grade> findGradesByMemberTotalPayment(int totalPayment);
}
