package com.beyond.twopercent.twofaang.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_review")
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @Column(name = "member_id")
    private int memberId;    //회원번호

    @Column(name = "product_id")
    private int productId; // 상품번호

    @Column(name = "rating")
    private int rating;     // 평점

    @Column(name = "review_text")
    private String reviewText;     // 리뷰 내용

    @Column(name = "review_date")
    private Date reviewDate;   // 작성일

    @Column(name = "update_date")
    private Date updateDate;  // 수정일
}