package com.beyond.twopercent.twofaang.product.dto;

import com.beyond.twopercent.twofaang.product.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProductDto {

    private long productId; // 상품번호

    private long categoryId; // 카테고리번호

    private String keyword;

    private String productName; // 상품이름

    private LocalDateTime registerDate; //등록일

    private LocalDateTime updateDate; // 수정일

    private String description; // 상품설명

    private int quantity; // 재고 수량

    private int price; // 상품 가격

    private boolean saleYn; // 할인여부

    private double discountRate; //할인율

    private LocalDate discountEndDate; //할인 종료일

    private int reviewCnt; // 리뷰수

    private int reviewScore; //별점 총합

    private int salesCnt; // 판매량

    private int viewCnt; // 조회수

    private String filename;
    private String urlFilename;


    public static ProductDto of(Product product){

        return ProductDto.builder()
                .productId(product.getProductId())
                .categoryId(product.getCategoryId())
                .keyword(product.getKeyword())
                .productName(product.getProductName())
                .registerDate(product.getRegisterDate())
                .updateDate(product.getUpdateDate())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .saleYn(product.isSaleYn())
                .discountRate(product.getDiscountRate())
                .discountEndDate(product.getDiscountEndDate())
                .reviewCnt(product.getReviewCnt())
                .salesCnt(product.getSalesCnt())
                .viewCnt(product.getViewCnt())
                .reviewScore(product.getReviewScore())
                .filename(product.getFilename())
                .urlFilename(product.getUrlFilename())
                .build();
    }

    public static List<ProductDto> of(List<Product> products) {
        if (products == null) {
            return null;
        }
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            productDtoList.add(of(product));
        }
        return productDtoList;
    }
}
