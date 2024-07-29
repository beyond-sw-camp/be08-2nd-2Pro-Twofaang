package com.beyond.twopercent.twofaang.review.service;


import com.beyond.twopercent.twofaang.review.dto.ReviewModifyRequestDto;
import com.beyond.twopercent.twofaang.review.dto.ReviewRequestDto;
import com.beyond.twopercent.twofaang.review.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<ReviewEntity> getAllReviews();

    Optional<ReviewEntity> getReviewById(Long reviewId);

    // 하나의 상품의 전체 리뷰 조회
    Optional<List<ReviewEntity>> getReviewsByProductId(Long productId);

    //회원의 리뷰 목록 조회
    Optional<List<ReviewEntity>> getReviewsByMemberId(Long memberId);

    ReviewEntity addReview(ReviewRequestDto request);

    ReviewEntity updateReview(Long reviewId, ReviewModifyRequestDto request);

    void deleteReview(ReviewEntity review);

}