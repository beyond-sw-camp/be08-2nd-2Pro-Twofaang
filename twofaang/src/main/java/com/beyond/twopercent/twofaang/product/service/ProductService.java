package com.beyond.twopercent.twofaang.product.service;

import com.beyond.twopercent.twofaang.product.dto.ProductAddDTO;

public interface ProductService {
    /*
        상품 추가
     */
    boolean add(ProductAddDTO parameter);
}
