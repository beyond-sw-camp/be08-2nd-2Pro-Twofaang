package com.beyond.twopercent.twofaang.inquiry.dto;

import com.beyond.twopercent.twofaang.inquiry.entity.InquiryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInquiryDto {

    @NotNull
    private long memberId; // 회원 번호

    @NotNull
    private long productId; // 상품번호

    @NotBlank
    private String inquiryTitle; // 제목

    @NotBlank
    private String inquiryText; // 내용

    @NotNull
    private InquiryType inquiryType; // 문의유형

    @NotBlank
    private String inquiryPassword; // 문의 비밀번호
}
