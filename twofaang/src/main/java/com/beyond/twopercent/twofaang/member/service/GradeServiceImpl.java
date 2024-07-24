package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.GradeRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import com.beyond.twopercent.twofaang.member.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    @Override
    public Grade createGrade(GradeRequestDto requestDto) {

        Grade grade = new Grade();
        grade.setGradeName(requestDto.getGradeName());
        grade.setDiscountRate(requestDto.getDiscountRate());
        grade.setTargetAmount(requestDto.getTargetAmount());

        return gradeRepository.save(grade);
    }

    @Override
    public Grade getGradeByGradeName(GradeName gradeName) {
        return gradeRepository.findByGradeName(gradeName);
    }

    @Override
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade updateGrade(Long id, Grade updatedGrade) {
        if (gradeRepository.existsById(id)) {
            updatedGrade.setGradeId(id);
            return gradeRepository.save(updatedGrade);
        }
        return null;
    }
}