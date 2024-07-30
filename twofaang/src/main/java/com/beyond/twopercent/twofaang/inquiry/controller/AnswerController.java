package com.beyond.twopercent.twofaang.inquiry.controller;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Answer;
import com.beyond.twopercent.twofaang.inquiry.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "답변 서비스 APIs", description = "답변 서비스 API 리스트")
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/list")
    @Operation(summary = "답변 조회", description = "전체 답변 조회")
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @PostMapping("/add")
    @Operation(summary = "답변 작성", description = "문의글에 대한 답변 작성")
    public ResponseEntity<String> addAnswer(@RequestBody CreateAnswerDto createAnswerDto, @RequestParam long memberId) {
        answerService.addAnswer(createAnswerDto, memberId);
        return ResponseEntity.status(201).body("답변이 성공적으로 추가되었습니다.");
    }

    @PutMapping("/update/{answer_id}")
    @Operation(summary = "답변 수정", description = "문의글에 대한 답변 수정")
    @Parameters(value = {
            @Parameter(name = "answer_id", description = "답변 번호", example = "1"),
            @Parameter(name = "memberId", description = "회원 번호", example = "1")
    })
    public ResponseEntity<String> updateAnswer(@PathVariable("answer_id") long answerId, @RequestBody UpdateAnswerDto updateAnswerDto, @RequestParam long memberId) {
        answerService.updateAnswer(answerId, updateAnswerDto, memberId);
        return ResponseEntity.ok("답변이 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/delete/{answer_id}")
    @Operation(summary = "답변 삭제", description = "문의글에 대한 답변 삭제")
    @Parameters(value = {
            @Parameter(name = "answer_id", description = "답변 번호", example = "1"),
            @Parameter(name = "memberId", description = "회원 번호", example = "1"),
    })
    public ResponseEntity<String> deleteAnswer(@PathVariable("answer_id") long answerId, @RequestParam long memberId) {
        answerService.deleteAnswer(answerId, memberId);
        return ResponseEntity.ok("답변이 성공적으로 삭제되었습니다.");
    }
}
