package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.GradeRequestDto;
import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.entity.enums.GradeName;
import com.beyond.twopercent.twofaang.member.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
@Tag(name = "등급 서비스 APIs", description = "등급 서비스 API 리스트")
public class GradeController {

    private final GradeService gradeService;

    // 모든 Grade 조회
    @GetMapping
    @Operation(summary = "모든 등급 조회", description = "모든 등급 정보를 조회한다.")
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    // GradeName으로 Grade 조회
    @GetMapping("/{gradeName}")
    @Operation(summary = "등급명으로 등급 조회", description = "등급명에 맞는 정보를 조회한다.")
    public ResponseEntity<Grade> getGradeByGradeName(@PathVariable GradeName gradeName) {
        Grade grade = gradeService.getGradeByGradeName(gradeName);
        return grade != null ? ResponseEntity.ok(grade) : ResponseEntity.notFound().build();
    }
}
