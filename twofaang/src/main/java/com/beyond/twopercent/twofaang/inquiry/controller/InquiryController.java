package com.beyond.twopercent.twofaang.inquiry.controller;

import com.beyond.twopercent.twofaang.inquiry.dto.CreateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.dto.InquiryResponseDto;
import com.beyond.twopercent.twofaang.inquiry.dto.UpdateInquiryDto;
import com.beyond.twopercent.twofaang.inquiry.service.InquiryService;
import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "문의 서비스 APIs", description = "설명")
@RequestMapping("/inquiries")
public class InquiryController {

    private final InquiryService inquiryService;


    //전체 문의 조회
    @GetMapping("/list")
    @Operation(summary = "문의 조회", description = "전체 문의 조회")
    public ResponseEntity<List<InquiryResponseDto>> getInquiries() {
        List<InquiryResponseDto> inquiries = inquiryService.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    //회원의 모든 문의
    @GetMapping("/list/{email}")
    @Operation(summary = "회원의 문의 조회", description = "현재 로그인된 회원의 모든 문의 조회")
    public ResponseEntity<List<InquiryResponseDto>> getInquiriesForLoggedInMember(
            @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        // 이메일 조회
        String email = customMemberDetails.getEmail();

        // 문의조회
        List<InquiryResponseDto> inquiries = inquiryService.getInquiriesByMember(email);

        return ResponseEntity.ok(inquiries);
    }


    //문의글 작성
    @PostMapping("/add")
    @Operation(summary = "문의 작성", description = "새로운 문의글 작성")
    public ResponseEntity<String> addInquiry(@RequestBody CreateInquiryDto createInquiryDto, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("문의글 작성 권한이 없습니다.");
        }
        // 인증된 사용자의 이메일을 가져옴
        String email = customMemberDetails.getEmail();
        inquiryService.addInquiry(createInquiryDto, email);

        return ResponseEntity.status(HttpStatus.CREATED).body("문의글이 작성되었습니다.");
    }

    //문의글 수정
    @PutMapping("/update/{inquiry_id}")
    @Operation(summary = "문의 수정", description = "문의글 수정")
    @Parameters(value = {
            @Parameter(name = "inquiry_id", description = "게시글 번호", example = "1")
    })
    public ResponseEntity<String> updateInquiry(
            @PathVariable("inquiry_id") long inquiryId, @RequestBody UpdateInquiryDto updateInquiryDto, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("문의글 수정 권한이 없습니다.");
        }
        String email = customMemberDetails.getEmail();
        inquiryService.updateInquiry(inquiryId, updateInquiryDto, email);
        return ResponseEntity.ok("문의글이 수정되었습니다.");
    }

    //문의글 삭제
    @DeleteMapping("/delete/{inquiry_id}")
    @Operation(summary = "문의 삭제", description = "문의글 삭제")
    @Parameters(value = {
            @Parameter(name = "inquiry_id", description = "게시글 번호", example = "1"),
            @Parameter(name = "inquiryPassword", description = "게시글 비밀번호", example = "1234")
    })
    public ResponseEntity<String> deleteInquiry(
            @PathVariable("inquiry_id") long inquiryId, @RequestParam String inquiryPassword, @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        if (customMemberDetails == null) {  //멤버가 아닐경우
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("문의글 삭제 권한이 없습니다.");
        }
        String email = customMemberDetails.getEmail(); //작성자가 아닐경우
        if (!inquiryService.isOwner(inquiryId, email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("문의글 삭제 권한이 없습니다.");
        }

        if (!inquiryService.verifyPassword(inquiryId, inquiryPassword)) { //비밀번호가 틀린경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        inquiryService.deleteInquiry(inquiryId, email, inquiryPassword);
        return ResponseEntity.ok("문의글이 삭제되었습니다.");
    }
}
