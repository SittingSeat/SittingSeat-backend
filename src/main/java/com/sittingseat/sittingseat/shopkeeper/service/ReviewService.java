package com.sittingseat.sittingseat.shopkeeper.service;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.domain.Review;
import com.sittingseat.sittingseat.exception.SittingSeatErrorCode;
import com.sittingseat.sittingseat.exception.SittingSeatException;
import com.sittingseat.sittingseat.shopkeeper.domain.Restaurant;
import com.sittingseat.sittingseat.shopkeeper.dtos.ReviewRequest;
import com.sittingseat.sittingseat.shopkeeper.dtos.ReviewResponse;
import com.sittingseat.sittingseat.shopkeeper.repository.RestaurantRepository;
import com.sittingseat.sittingseat.shopkeeper.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void addReview(ReviewRequest reviewRequest, Member writer){
        Long restaurantId = reviewRequest.getRestaurantId();
        Restaurant targetRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new SittingSeatException(SittingSeatErrorCode.RESTAURANT_NOT_FOUND));

        Review review = Review.builder()
                .content(reviewRequest.getContent())
                .score(reviewRequest.getScore())
                .member(writer)
                .restaurant(targetRestaurant)
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void updateReview(ReviewRequest reviewRequest, Long reviewId){
        Review updateReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new SittingSeatException(SittingSeatErrorCode.REVIEW_NOT_FOUND));
        updateReview.updateReview(reviewRequest.getScore(), reviewRequest.getContent());
    }

    @Transactional
    public void deleteReview(Long reviewId){
        reviewRepository.deleteById(reviewId);
    }

    public List<ReviewResponse> findByMember(Member member){
        return reviewRepository.findByMember(member).stream()
                .map(r -> new ReviewResponse(r.getRestaurant().getName(), r.getScore(), r.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void replyReview(){

    }
}
