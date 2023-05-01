package com.sittingseat.sittingseat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class KakaoToken {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private Integer refreshTokenExpiresIn;
}
