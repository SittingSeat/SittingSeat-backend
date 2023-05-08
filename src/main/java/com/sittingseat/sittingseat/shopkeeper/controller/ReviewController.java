package com.sittingseat.sittingseat.shopkeeper.controller;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.security.auth.PrincipalDetails;
import com.sittingseat.sittingseat.shopkeeper.dtos.ReviewRequest;
import com.sittingseat.sittingseat.shopkeeper.dtos.ReviewResponse;
import com.sittingseat.sittingseat.shopkeeper.service.ReviewService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<Void> addReview(@RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Member loginMember = principalDetails.getMember();
        reviewService.addReview(reviewRequest, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest){
        reviewService.updateReview(reviewRequest, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/my-reviews")
    public ResponseEntity<List<ReviewResponse>> findReviewsByMember(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Member loginMember = principalDetails.getMember();
        List<ReviewResponse> findReviews = reviewService.findByMember(loginMember);
        return new ResponseEntity<>(findReviews, HttpStatus.OK);
    }

    /**
     * TODO shopkeeper's reply
     */



}
