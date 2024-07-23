package com.beyond.twopercent.twofaang.member.repository;

import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    // GradeName으로 Grade를 찾기 위한 메소드 추가
    Grade findByGradeName(GradeName gradeName);
}
