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
@Table(name = "tb_inquiry")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private long inquiryId; //게시글 번호


    @Column(name = "member_id")
    private long memberId; //회원 번호


    @Column(name = "product_id")
    private long productId; //상품번호

    @Column(name = "inquiry_title")
    private String inquiryTitle; //제목

    @Lob
    @Column(name = "inquiry_text")
    private String inquiryText; // 내용

    @Enumerated(EnumType.STRING)
    @Column(name = "inquiry_type")
    private InquiryType inquiryType; //문의유형

    @Column(name = "inquiry_date")
    private LocalDateTime inquiryDate; // 문의일

    @Column(name = "inquiry_password")
    private String inquiryPassword; // 문의 비밀번호

}
