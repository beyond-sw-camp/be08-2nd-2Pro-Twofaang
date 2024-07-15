package com.beyond.twopercent.twofaang.inquiry.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "tb_answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "answer_id")
    private long answerId; //답변번호
    @Column(name = "inquiry_id")
    private long inquiryId; //문의번호
    @Column(name = "member_id")
    private long memberId; //회원번호

    @Lob
    @Column(name = "comment")
    private String comment; //답변내용

    @Column(name = "response_date")
    private LocalDateTime responseDate; //답변일

    @Column(name = "update_date")
    private LocalDateTime updateDate; //수정일
}