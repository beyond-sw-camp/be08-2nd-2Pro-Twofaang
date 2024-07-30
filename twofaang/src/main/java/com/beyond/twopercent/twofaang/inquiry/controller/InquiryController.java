package com.beyond.twopercent.twofaang.inquiry.controller;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.entity.Inquiry;
import com.beyond.twopercent.twofaang.inquiry.service.InquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "문의 서비스 APIs", description = "문의 서비스 API 리스트")
@RequestMapping("/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/list")
    @Operation(summary = "문의 조회", description = "전체 문의 조회")
    public ResponseEntity<List<Inquiry>> getInquiries() {
        List<Inquiry> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @PostMapping("/add")
    @Operation(summary = "문의 작성", description = "새로운 문의글 작성")
    public ResponseEntity<String> addInquiry(@RequestBody CreateInquiryDto createInquiryDto) {
        inquiryService.addInquiry(createInquiryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("문의글이 작성되었습니다.");
    }

    @PutMapping("/update/{inquiry_id}")
    @Operation(summary = "문의 수정", description = "문의글 수정")
    @Parameters(value = {
            @Parameter(name = "inquiry_id", description = "게시글 번호", example = "1"),
            @Parameter(name = "memberId", description = "회원번호", example = "1")
    })
    public ResponseEntity<String> updateInquiry(@PathVariable("inquiry_id") long inquiryId, @RequestParam long memberId, @RequestBody UpdateInquiryDto updateInquiryDto) {
        if (!inquiryService.isOwner(inquiryId, memberId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("문의글 수정 권한이 없습니다.");
        }

        inquiryService.updateInquiry(inquiryId, updateInquiryDto, memberId);
        return ResponseEntity.ok("문의글이 수정되었습니다.");
    }

    @DeleteMapping("/delete/{inquiry_id}")
    @Operation(summary = "문의 삭제", description = "문의글 삭제")
    @Parameters(value = {
            @Parameter(name = "inquiry_id", description = "게시글 번호", example = "1"),
            @Parameter(name = "memberId", description = "회원번호", example = "1"),
            @Parameter(name = "inquiryPassword", description = "게시글 비밀번호", example = "1234")
    })
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
