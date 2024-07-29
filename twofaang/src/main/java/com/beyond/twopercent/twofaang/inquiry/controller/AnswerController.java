package com.beyond.twopercent.twofaang.inquiry.controller;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateAnswerDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Answer;
import com.beyond.twopercent.twofaang.inquiry.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/list")
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAnswer(@RequestBody CreateAnswerDto createAnswerDto, @RequestParam long memberId) {
        answerService.addAnswer(createAnswerDto, memberId);
        return ResponseEntity.status(201).body("답변이 성공적으로 추가되었습니다.");
    }

    @PutMapping("/update/{answer_id}")
    public ResponseEntity<String> updateAnswer(@PathVariable("answer_id") long answerId, @RequestBody UpdateAnswerDto updateAnswerDto, @RequestParam long memberId) {
        answerService.updateAnswer(answerId, updateAnswerDto, memberId);
        return ResponseEntity.ok("답변이 성공적으로 업데이트되었습니다.");
    }

    @DeleteMapping("/delete/{answer_id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable("answer_id") long answerId, @RequestParam long memberId) {
        answerService.deleteAnswer(answerId, memberId);
        return ResponseEntity.ok("답변이 성공적으로 삭제되었습니다.");
    }
}
