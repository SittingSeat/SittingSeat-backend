package com.sittingseat.sittingseat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 30 * 24 * 60 * 60)
@RequiredArgsConstructor
public class RefreshToken {
    @org.springframework.data.annotation.Id
    private final String email;

    private final String refreshToken;
}
