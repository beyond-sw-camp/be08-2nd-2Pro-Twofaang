package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.GradeRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import com.beyond.twopercent.twofaang.member.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    // 모든 Grade 조회
    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    // GradeName으로 Grade 조회
    @GetMapping("/{gradeName}")
    public ResponseEntity<Grade> getGradeByGradeName(@PathVariable GradeName gradeName) {
        Grade grade = gradeService.getGradeByGradeName(gradeName);
        return grade != null ? ResponseEntity.ok(grade) : ResponseEntity.notFound().build();
    }

    // Grade 추가
    @PostMapping
    public Grade createGrade(@RequestBody GradeRequestDto grade) {
        return gradeService.createGrade(grade);
    }

    // Grade 수정
    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade updatedGrade) {
        Grade grade = gradeService.updateGrade(id, updatedGrade);
        return grade != null ? ResponseEntity.ok(grade) : ResponseEntity.notFound().build();
    }
}
