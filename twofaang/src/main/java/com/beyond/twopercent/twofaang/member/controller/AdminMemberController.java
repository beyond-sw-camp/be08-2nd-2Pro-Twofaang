package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.GradeRequestDto;
import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Grade;
import com.beyond.twopercent.twofaang.member.service.GradeService;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "관리자 서비스 APIs", description = "관리자 서비스 API 리스트")
public class AdminMemberController {

    private final MemberService memberService;
    private final GradeService gradeService;

    // 모든 회원 정보를 반환
    @GetMapping("/members/list")
    @Operation(summary = "모든 회원 정보 조회", description = "모든 회원 정보를 조회한다.")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    // 이메일로 회원 정보 반환
    @GetMapping("/members/{email}")
    @Operation(summary = "이메일로 회원 정보 조회", description = "이메일로 회원 정보를 조회한다.")
    public ResponseEntity<MemberResponseDto> getMemberByEmail(
            @PathVariable String email
    ) {
        MemberResponseDto member = memberService.getMemberByEmail(email);
        return ResponseEntity.ok(member);
    }

    // 회원 상태 변경
    @PutMapping("/members/status/{email}")
    @Operation(summary = "회원 상태 변경", description = "회원의 활성 상태 여부를 수정한다.")
    public ResponseEntity<MemberResponseDto> updateMemberStatus(
            @PathVariable String email,
            @RequestParam String status
    ) {
        MemberResponseDto updatedMember = memberService.updateMemberStatus(email, status);
        return ResponseEntity.ok(updatedMember);
    }

    // Grade 추가
    @PostMapping("/add/grade")
    @Operation(summary = "등급 추가", description = "등급을 추가한다.")
    public Grade createGrade(@RequestBody GradeRequestDto grade) {
        return gradeService.createGrade(grade);
    }

    // Grade 수정
    @PutMapping("/grade/update/{id}")
    @Operation(summary = "등급 수정", description = "등급의 정보를 수정한다.")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade updatedGrade) {
        Grade grade = gradeService.updateGrade(id, updatedGrade);
        return grade != null ? ResponseEntity.ok(grade) : ResponseEntity.notFound().build();
    }
}
