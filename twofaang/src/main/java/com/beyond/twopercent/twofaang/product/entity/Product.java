package com.beyond.twopercent.twofaang.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId; // 상품번호

    @Column(name = "category_id")
    private long categoryId; // 카테고리번호

    @Column(name = "product_name")
    private String productName; // 상품이름

    @Column(name = "register_date")
    private LocalDateTime registerDate; //등록일

    @Column(name = "update_date")
    private LocalDateTime updateDate; // 수정일

    @Lob
    @Column(name = "description")
    private String description; // 상품설명

    @Column(name = "quantity")
    private int quantity; // 재고 수량

    @Column(name = "price")
    private int price; // 상품 가격

    @Column(name = "is_discount")
    private boolean saleYn; // 할인여부

    @Column(name = "discount_rate")
    private double discountRate; //할인율

    @Column(name = "discount_end_date")
    private LocalDate discountEndDate; //할인 종료일

    @Column(name = "review_cnt")
    private int reviewCnt; // 리뷰수

    @Column(name = "sales_cnt")
    private int salesCnt; // 판매량

    @Column(name = "view_cnt")
    private int viewCnt; // 조회수

    private String filename;
    private String urlFilename;

}