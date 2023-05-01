package com.sittingseat.sittingseat.controller;

import com.sittingseat.sittingseat.dto.KakaoMemberDto;
import com.sittingseat.sittingseat.dto.KakaoToken;
import com.sittingseat.sittingseat.dto.TokenInfo;
import com.sittingseat.sittingseat.service.MemberService;
import com.sittingseat.sittingseat.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;
    private final MemberService memberService;

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
