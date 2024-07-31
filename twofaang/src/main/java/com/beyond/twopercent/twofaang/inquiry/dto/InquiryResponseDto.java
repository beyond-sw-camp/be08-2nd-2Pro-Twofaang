package com.beyond.twopercent.twofaang.inquiry.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InquiryResponseDto { //모든 리스트 조회시 불러올 데이터
    private long inquiryId;

    private String memberEmail;

    private Long productId;

    private String inquiryTitle;

    private String inquiryText;

    private String inquiryType;

    private LocalDateTime inquiryDate;

}
