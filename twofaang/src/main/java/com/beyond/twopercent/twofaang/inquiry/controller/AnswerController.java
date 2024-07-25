package com.beyond.twopercent.twofaang.inquiry.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.beyond.twopercent.twofaang.inquiry.entity.Answer;
import com.beyond.twopercent.twofaang.inquiry.service.AnswerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    // 특정 문의의 모든 답변 조회
    @GetMapping("/inquiry/{inquiry_id}")
    public ResponseEntity<List<Answer>> getAnswersByInquiryId(@PathVariable("inquiry_id") long inquiryId) {
        List<Answer> answers = answerService.getAnswersByInquiryId(inquiryId);
        return ResponseEntity.ok(answers);
    }

    // 특정 답변 조회
    @GetMapping("/{answer_id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable("answer_id") long answerId) {
        Answer answer = answerService.getAnswerById(answerId);
        if (answer != null) {
            return ResponseEntity.ok(answer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 답변 작성
    @PostMapping("/add")
    public ResponseEntity<String> addAnswer(@RequestBody Answer answer) {
        int result = answerService.addAnswer(answer);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("답변이 작성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 작성 실패.");
        }
    }

    // 답변 수정
    @PutMapping("/{answer_id}")
    public ResponseEntity<String> updateAnswer(@PathVariable("answer_id") long answerId, @RequestBody Answer answer) {
        answer.setAnswerId(answerId);
        int result = answerService.updateAnswer(answer);
        if (result > 0) {
            return ResponseEntity.ok("답변이 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 수정 실패.");
        }
    }

    // 답변 삭제
    @DeleteMapping("/{answer_id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable("answer_id") long answerId) {
        int result = answerService.deleteAnswer(answerId);
        if (result > 0) {
            return ResponseEntity.ok("답변이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 삭제 실패.");
        }
    }
}