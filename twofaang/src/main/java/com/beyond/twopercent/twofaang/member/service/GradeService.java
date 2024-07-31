package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.GradeRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;

import java.util.List;
import java.util.Optional;

public interface GradeService {
    Grade createGrade(GradeRequestDto requestDto);
    Grade getGradeByGradeName(GradeName gradeName);
    List<Grade> getAllGrades();
    Grade updateGrade(Long id, Grade updatedGrade);
    Grade getMyGrade(String email);
}
