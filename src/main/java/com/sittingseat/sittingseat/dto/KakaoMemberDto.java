package com.sittingseat.sittingseat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class KakaoMemberDto {
    private String nickname;
    private String name;
    private String password;
    private String email;
    private boolean isFirst;
}
