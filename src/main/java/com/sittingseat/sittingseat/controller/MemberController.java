package com.sittingseat.sittingseat.controller;

import com.sittingseat.sittingseat.dto.MemberJoinDto;
import com.sittingseat.sittingseat.dto.MemberLoginDto;
import com.sittingseat.sittingseat.dto.TokenInfo;
import com.sittingseat.sittingseat.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "회원 관련 API 컨트롤러")
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입 API")
    @ApiResponse(code = 200, message = "회원가입 성공")
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody MemberJoinDto memberJoinDto){
        memberService.join(memberJoinDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody MemberLoginDto memberLoginDto){
        TokenInfo tokenInfo = memberService.login(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        return new ResponseEntity<>(tokenInfo, HttpStatus.OK);
    }
}
