package com.beyond.twopercent.twofaang.review.service;

import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import com.beyond.twopercent.twofaang.review.dto.ReviewModifyRequestDto;
import com.beyond.twopercent.twofaang.review.dto.ReviewRequestDto;
import com.beyond.twopercent.twofaang.review.entity.ReviewEntity;
import com.beyond.twopercent.twofaang.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    // private ^final^ 붙여줘야 해~!
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;


    @Override
    public List<ReviewEntity> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<ReviewEntity> getReviewById(Long reviewId) {
        Optional<ReviewEntity> review = reviewRepository.findById(reviewId);
        return review;
    }

    @Override
    public Optional<List<ReviewEntity>> getReviewsByProductId(Long productId) {
        return reviewRepository.findAllByProductId(productId);
    }

    @Override
    public Optional<List<ReviewEntity>> getReviewsByMemberId(Long memberId) {
        return reviewRepository.findAllByMemberId(memberId);
    }

    @Override
    public ReviewEntity addReview(ReviewRequestDto request) {
        ReviewEntity review = ReviewEntity.builder()
                .memberId(request.getMemberId())
                .productId(request.getProductId())
                .rating(request.getRating())
                .reviewText(request.getReviewText())
                .build();
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
        Product product = optionalProduct.get();
        product.setReviewCnt(product.getReviewCnt() + 1);
        product.setReviewScore(product.getReviewScore() + review.getRating());
        productRepository.save(product);
        return reviewRepository.save(review);
    }

    @Override
    public ReviewEntity updateReview(Long reviewId, ReviewModifyRequestDto request) {
        ReviewEntity existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id " + reviewId));


        if (request.getRating() != 0) {
            existingReview.setRating(request.getRating());
        }
        if (request.getReviewText() != null) {
            existingReview.setReviewText(request.getReviewText());
        }

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(ReviewEntity review) {
        Optional<Product> optionalProduct = productRepository.findById(review.getProductId());
        Product product = optionalProduct.get();
        product.setReviewCnt(product.getReviewCnt() - 1);
        productRepository.save(product);
        reviewRepository.deleteById(review.getReviewId());
    }

}

