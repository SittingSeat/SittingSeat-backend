package com.sittingseat.sittingseat.shopkeeper.controller;

import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.security.auth.PrincipalDetails;
import com.sittingseat.sittingseat.shopkeeper.dtos.ReviewRequest;
import com.sittingseat.sittingseat.shopkeeper.dtos.ReviewResponse;
import com.sittingseat.sittingseat.shopkeeper.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "리뷰 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 등록 API", description = "ReviewRequest를 받아서 리뷰 등록을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰 등록 성공")
    })
    @PostMapping("/add")
    public ResponseEntity<Void> addReview(@RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Member loginMember = principalDetails.getMember();
        reviewService.addReview(reviewRequest, loginMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "리뷰 삭제 API", description = "PathVariable로 review id 받아서 리뷰 삭제를 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰 삭제 성공")
    })
    @PostMapping("/update/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequest reviewRequest){
        reviewService.updateReview(reviewRequest, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "내가 작성한 리뷰 검색 API", description = "ReviewRequest를 받아서 리뷰 등록을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "내가 작성한 리뷰 조회 성공")
    })
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
