package com.beyond.twopercent.twofaang.inquiry.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryEntity {

    private long inquiryId; //게시글 번호


    private long memberId; //회원 번호

    private long productId; //상품번호


    private String inquiryTitle; //제목


    private String inquiryText; // 내용


    private InquiryType inquiryType; //문의유형


    private LocalDateTime inquiryDate; // 문의일
}