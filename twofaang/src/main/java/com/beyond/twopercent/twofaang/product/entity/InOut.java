package com.beyond.twopercent.twofaang.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_inout")
public class InOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inout_id")
    private Long inoutId; // 입출고 번호

    @Column(name = "product_id")
    private Long productId; // 상품 번호

    @Column(name = "inout_type")
    @Enumerated(EnumType.STRING)
    private InoutType inoutType; // 입출고 유형

    @Column(name = "quantity")
    private int quantity; // 수량

    @Column(name = "inout_date")
    private Date inoutDate; // 입출고 일
}