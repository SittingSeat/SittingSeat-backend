package com.sittingseat.sittingseat.controller;

import com.sittingseat.sittingseat.dto.KakaoMemberDto;
import com.sittingseat.sittingseat.dto.KakaoToken;
import com.sittingseat.sittingseat.dto.TokenInfo;
import com.sittingseat.sittingseat.service.MemberService;
import com.sittingseat.sittingseat.service.OauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "KAKAO 연동 로그인 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;
    private final MemberService memberService;

    @Operation(summary = "KAKAO 연동 로그인", description = "카카오 연동 로그인 페이지에서 로그인 시 해당 API 자동 호출(Redirect URL) 후 강제 로그인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카카오 로그인 성공"),
            @ApiResponse(code = 500, message = "카카오 로그인 에러")
        }
    )
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<TokenInfo> getKakaoCode(@RequestParam(value = "code") String code){
        log.info("code={}", code);
        /**
         * TODO Kakao Login
         */
        KakaoToken kakaoAccessToken = oauthService.getKakaoAccessToken(code);
        KakaoMemberDto kakaoUser = oauthService.createKakaoUser(kakaoAccessToken.getAccessToken());
        TokenInfo tokenInfo = memberService.login(kakaoUser.getEmail(), "qwe123!!");
        log.info("tokenInfo={}", tokenInfo);
        return new ResponseEntity<>(tokenInfo, HttpStatus.OK);

    }

}
