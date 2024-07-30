package com.beyond.twopercent.twofaang.review.controller;

import com.beyond.twopercent.twofaang.review.dto.ReviewModifyRequestDto;
import com.beyond.twopercent.twofaang.review.entity.ReviewEntity;
import com.beyond.twopercent.twofaang.review.dto.ReviewRequestDto;
import com.beyond.twopercent.twofaang.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "ReviewController", description = "리뷰 관련 API를 제공하는 컨트롤러")
public class ReviewController {

    private final ReviewService reviewService;

    // 모든 리뷰 조회
    @GetMapping("/list")
    @Operation(summary = "모든 리뷰 조회", description = "모든 리뷰를 조회한다.")
    public List<ReviewEntity> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // 멤버 id로 리뷰 조회
    @GetMapping("/member/{memberId}")
    @Operation(summary = "멤버 ID로 리뷰 조회", description = "멤버 ID를 기반으로 리뷰를 조회한다.")
    public Optional<List<ReviewEntity>> getAllReviewsByMemberId(@PathVariable("memberId") Long memberId) {
        return reviewService.getReviewsByMemberId(memberId);
    }

    // 상품 id로 리뷰 조회
    @GetMapping("/product/{productId}")
    @Operation(summary = "상품 ID로 리뷰 조회", description = "상품 ID를 기반으로 리뷰를 조회한다.")
    public Optional<List<ReviewEntity>> getAllReviewsByProductId(@PathVariable("productId") Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    // 특정 리뷰 조회
    @GetMapping("/{review_id}")
    @Operation(summary = "특정 리뷰 조회", description = "리뷰 ID를 통해 특정 리뷰를 조회한다.")
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
    @Operation(summary = "리뷰 등록", description = "리뷰 정보를 JSON으로 받아서 등록한다.")
    public ReviewEntity addReview(@RequestBody ReviewRequestDto request) {
        return reviewService.addReview(request);
    }

    // reviewId로 리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID를 통해 특정 리뷰를 삭제한다.")
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
    @Operation(summary = "리뷰 수정", description = "리뷰 ID와 수정 정보를 받아 리뷰를 수정한다.")
    public ResponseEntity<ReviewEntity> updateReview(@PathVariable Long reviewId, @RequestBody ReviewModifyRequestDto request) {
        ReviewEntity reviewEntity = reviewService.updateReview(reviewId, request);
        return ResponseEntity.ok(reviewEntity);
    }

}
