package com.beyond.twopercent.twofaang.product.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class ProductAddDto {

    private Long productId;

    private Long categoryId;

    private String categoryName;

    private String productName; // 상품 이름

    private LocalDateTime registerDate; // 등록일

    private LocalDateTime updateDate; // 수정일

    private String description; // 상품 설명

    private int quantity; // 재고 수량

    private int price; // 상품 가격

    private Boolean saleYn; // 할인여부

    private double discountRate; //할인율

    private String discountEndDateText; //할인 종료일

    private String filename;
    private String urlFilename;

}
