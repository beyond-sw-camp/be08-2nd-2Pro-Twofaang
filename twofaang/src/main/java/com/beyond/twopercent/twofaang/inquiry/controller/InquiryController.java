package com.beyond.twopercent.twofaang.inquiry.controller;

import com.beyond.twopercent.twofaang.inquiry.entity.InquiryEntity;
import com.beyond.twopercent.twofaang.inquiry.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    // 모든 문의 조회
    @GetMapping("/list")
    public ResponseEntity<List<InquiryEntity>> getInquiries() {
        List<InquiryEntity> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    // 특정 문의 조회
    @GetMapping("/{inquiry_id}")
    public ResponseEntity<InquiryEntity> getInquiry(@PathVariable("inquiry_id") long inquiryId) {
        InquiryEntity inquiry = inquiryService.getInquiryById(inquiryId);
        if (inquiry != null) {
            return ResponseEntity.ok(inquiry);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 문의글 작성
    @PostMapping("/add")
    public ResponseEntity<String> addInquiry(@RequestBody InquiryEntity inquiry) {
        int result = inquiryService.addInquiry(inquiry);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("문의글이 작성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의글 작성 실패.");
        }
    }

    // 문의글 수정
    @PutMapping("/{inquiry_id}")
    public ResponseEntity<String> updateInquiry(@PathVariable("inquiry_id") long inquiryId, @RequestBody InquiryEntity inquiry) {
        inquiry.setInquiryId(inquiryId);
        int result = inquiryService.updateInquiry(inquiry);
        if (result > 0) {
            return ResponseEntity.ok("문의글이 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의글 수정 실패.");
        }
    }

    // 문의글 삭제
    @DeleteMapping("/{inquiry_id}")
    public ResponseEntity<String> deleteInquiry(@PathVariable("inquiry_id") long inquiryId) {
        int result = inquiryService.deleteInquiry(inquiryId);
        if (result > 0) {
            return ResponseEntity.ok("문의글이 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의글 삭제 실패.");
        }
    }
}

