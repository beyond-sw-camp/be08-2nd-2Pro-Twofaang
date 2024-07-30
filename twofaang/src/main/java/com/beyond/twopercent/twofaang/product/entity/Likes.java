package com.beyond.twopercent.twofaang.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "tb_likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likesId;

    @Column(name = "member_id")
    private long memberId; // 회원번호

    @Column(name = "product_id")
    private long productId; // 상품번호

    @Column(name = "register_date")
    private LocalDateTime regDt; // 좋아요 누른 날짜
}
 