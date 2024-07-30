package com.beyond.twopercent.twofaang.product.service;

import com.beyond.twopercent.twofaang.product.dto.LikesDto;
import com.beyond.twopercent.twofaang.product.dto.ProductAddDto;
import com.beyond.twopercent.twofaang.product.dto.ProductDto;

public interface ProductService {
    /*
        상품 추가
     */
    boolean add(ProductAddDto parameter);

    /*
        상품 수정 Data Get
     */
    ProductDto update(long id);

    /*
        상품 수정
     */
    boolean updateProduct(ProductAddDto parameter);

    /*
        상품 삭제
     */
    boolean deleteProduct(long id);

    /*
        상품 상세
     */
    ProductDto detail(long id);

    /*
        상품 좋아요
     */
    boolean addLike(LikesDto parameter);
}
