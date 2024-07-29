package com.beyond.twopercent.twofaang.inquiry.controller;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;
import com.beyond.twopercent.twofaang.inquiry.service.InquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/list")
    public ResponseEntity<List<Inquiry>> getInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInquiry(@RequestBody CreateInquiryDto createInquiryDto) {
        inquiryService.addInquiry(createInquiryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("문의글이 작성되었습니다.");
    }

    @PutMapping("/update/{inquiry_id}")
    public ResponseEntity<String> updateInquiry(@PathVariable("inquiry_id") long inquiryId, @RequestParam long memberId, @RequestBody UpdateInquiryDto updateInquiryDto) {
        if (!inquiryService.isOwner(inquiryId, memberId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("문의글 수정 권한이 없습니다.");
        }

        inquiryService.updateInquiry(inquiryId, updateInquiryDto, memberId);
        return ResponseEntity.ok("문의글이 수정되었습니다.");
    }

    @DeleteMapping("/delete/{inquiry_id}")
    public ResponseEntity<String> deleteInquiry(@PathVariable("inquiry_id") long inquiryId, @RequestParam long memberId, @RequestParam String inquiryPassword) {
        if (!inquiryService.isOwner(inquiryId, memberId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("문의글 삭제 권한이 없습니다.");
        }

        if (!inquiryService.verifyPassword(inquiryId, inquiryPassword)) { // Correct method signature here
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        inquiryService.deleteInquiry(inquiryId, memberId, inquiryPassword);
        return ResponseEntity.ok("문의글이 삭제되었습니다.");
    }
}
