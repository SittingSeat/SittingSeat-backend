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
    MEMBER_NOT_FOUND(400, "해당 회원이 존재하지 않습니다.", "M001")


    ;

    private int status;
    private String message;
    private String code;
}
