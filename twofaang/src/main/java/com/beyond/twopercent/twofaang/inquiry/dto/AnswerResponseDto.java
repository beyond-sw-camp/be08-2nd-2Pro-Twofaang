package com.beyond.twopercent.twofaang.inquiry.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerResponseDto { //모든 답변 조회시 불러올 데이터 목록

    private long answerId;

    private long inquiryId;

    private String memberEmail;

    private String comment;

    private LocalDateTime responseDate;

    private LocalDateTime updateDate;
}
