package com.sittingseat.sittingseat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProviderEnum {
    EMAIL("EMAIL"), GOOGLE("GOOGLE"), NAVER("NAVER"), KAKAO("KAKAO") ;

    @JsonValue
    private final String provider;
}
