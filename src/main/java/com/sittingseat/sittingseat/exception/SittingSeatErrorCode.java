package com.sittingseat.sittingseat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SittingSeatErrorCode {
    // JWT
    UNAUTHORIZED_TOKEN(401, "권한이 없는 토큰입니다.", "J001"),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다.", "J002"),
    UNSUPPORTED_TOKEN(401, "지원하지 않는 토큰입니다.", "J003"),
    EMPTY_CLAIM_TOKEN(401, "클레임 정보가 없는 토큰입니다.", "J004"),
    EXPIRED_TOKEN(401, "만료된 토큰입니다.", "J005"),

    // Member
    MEMBER_NOT_FOUND(400, "해당 회원이 존재하지 않습니다.", "M001"),

    // Restaurant
    RESTAURANT_NOT_FOUND(400, "해당 가게가 존재하지 않습니다.", "RT001"),

    // Review
    REVIEW_NOT_FOUND(400, "해당 리뷰가 존재하지 않습니다.", "RV001"),

    // S3
    S3_ACCESS_DENIED(401, "S3 접근에 실패하였습니다.", "S001"),

    ;

    private int status;
    private String message;
    private String code;
}
