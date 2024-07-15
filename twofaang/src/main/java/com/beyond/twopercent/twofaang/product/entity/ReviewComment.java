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
@Table(name = "tb_review_comment")
@Builder
public class ReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "review_id")
    private int reviewId;    // 후기번호

    @Column(name = "member_id")
    private int memberId; // 회원번호

    @Column(name = "content")
    private String content;     // 평점

    @Column(name = "review_date")
    private Date reviewDate;   // 작성일

    @Column(name = "update_date")
    private Date updateDate;  // 수정일

    @Column(name = "is_deleted")
    private boolean isDeleted; // 삭제여부
}

  