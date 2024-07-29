package com.beyond.twopercent.twofaang.review.controller;

import com.beyond.twopercent.twofaang.review.dto.ReviewModifyRequestDto;
import com.beyond.twopercent.twofaang.review.entity.ReviewEntity;
import com.beyond.twopercent.twofaang.review.dto.ReviewRequestDto;
import com.beyond.twopercent.twofaang.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 모든 리뷰 조회
    @GetMapping("/list")
    public List<ReviewEntity> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // 멤버 id로 리뷰 조회
    @GetMapping("/member/{memberId}")
    public Optional<List<ReviewEntity>> getAllReviewsByMemberId(@PathVariable("memberId") Long memberId) {
        return reviewService.getReviewsByMemberId(memberId);
    }

    // 상품 id로 리뷰 조회
    @GetMapping("/product/{productId}")
    public Optional<List<ReviewEntity>> getAllReviewsByProductId(@PathVariable("productId") Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    // 특정 리뷰 조회
    @GetMapping("/{review_id}")
    public ResponseEntity<Optional<ReviewEntity>> getReview(@PathVariable("review_id") long reviewId) {
        Optional<ReviewEntity> review = reviewService.getReviewById(reviewId);
        if (review.isPresent()) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 리뷰 작성
    @PostMapping("/add")
    public ReviewEntity addReview(@RequestBody ReviewRequestDto request) {
        return reviewService.addReview(request);
    }

    // reviewId로 리뷰 삭제 - 첫 번째 메소드 제거
    // @DeleteMapping("/delete/{reviewId}")
    // public void deleteReview(@PathVariable Long reviewId) {
    // }

    // reviewId로 리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        Optional<ReviewEntity> review = reviewService.getReviewById(reviewId);
        if (review.isPresent()) {
            reviewService.deleteReview(review.orElse(null));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // reviewId로 리뷰 수정
    @PutMapping("/update/{reviewId}")
    public ResponseEntity<ReviewEntity> updateReview(@PathVariable Long reviewId, @RequestBody ReviewModifyRequestDto request) {
        ReviewEntity reviewEntity = reviewService.updateReview(reviewId, request);

        return ResponseEntity.ok(reviewEntity);
    }

}