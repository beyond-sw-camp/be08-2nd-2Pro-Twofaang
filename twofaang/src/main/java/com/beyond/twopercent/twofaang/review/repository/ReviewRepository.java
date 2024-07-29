package com.beyond.twopercent.twofaang.review.repository;


import com.beyond.twopercent.twofaang.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // 하나의 상품의 전체 리뷰 조회
    Optional<List<ReviewEntity>> findAllByProductId(Long productId);

    //회원의 리뷰 목록 조회
    Optional<List<ReviewEntity>> findAllByMemberId(Long memberId);

}