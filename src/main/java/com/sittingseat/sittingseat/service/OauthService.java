package com.sittingseat.sittingseat.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.dto.KakaoMemberDto;
import com.sittingseat.sittingseat.dto.KakaoToken;
import com.sittingseat.sittingseat.dto.TokenInfo;
import com.sittingseat.sittingseat.enums.ProviderEnum;
import com.sittingseat.sittingseat.repository.MemberRepository;
import com.sittingseat.sittingseat.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String authorizationGrantType;

    private KakaoToken kakaoToken;

    public KakaoToken getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + authorizationGrantType);
            sb.append("&client_id=" + clientId); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=" + redirectUri); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body={}", result);

            // Json Parsing with Gson
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(result, JsonObject.class);

            String accessToken = json.get("access_token").getAsString();
            String refreshToken = json.get("refresh_token").getAsString();
            String tokenType = json.get("token_type").getAsString();
            int expiresIn = Integer.parseInt(json.get("expires_in").getAsString());
            int refreshTokenExpiresIn = Integer.parseInt(json.get("refresh_token_expires_in").getAsString());
            log.info("refresh={}", refreshTokenExpiresIn);

            kakaoToken = KakaoToken.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType(tokenType)
                    .expiresIn(expiresIn)
                    .refreshTokenExpiresIn(refreshTokenExpiresIn)
                    .build();


            log.info("access_token={} ", access_Token);
            log.info("refresh_token={}", refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kakaoToken;
    }

    @Transactional
    public KakaoMemberDto createKakaoUser(String token){

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body={}", result);

            //Gson 라이브러리로 JSON파싱
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(result, JsonObject.class);

            long id = Long.parseLong(json.get("id").getAsString());
            boolean hasEmail = Boolean.parseBoolean(json.get("kakao_account").getAsJsonObject().get("has_email").getAsString());


            String email = "KAKAO" + id + "@kakao.com";
            String name = "";
            String nickname = "KAKAO" + id;
            String password = "qwe123!!!";

            name = json.get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();

            Optional<Member> findMember = memberRepository.findByKakaoNickname(nickname);
            if(findMember.isPresent()){
                // 이미 카카오 연동으로 로그인 되어있는 계정
                br.close();
                return KakaoMemberDto.builder()
                        .nickname(nickname)
                        .isFirst(false)
                        .build();

            } else{
                // 강제 회원가입
                if(hasEmail){
                    email = json.get("kakao_account").getAsJsonObject().get("email").getAsString();
                }
//            if(hasNickname){
//                nickname = JsonParser.parseString("kakao_account").getAsJsonObject().get("name").getAsString();
//            }

                log.info("email={}", email);
                log.info("name={}", name);

                Member kakaoMember = Member.builder()
                        .name(name)
                        .email(email)
                        .phone("")
                        .password(passwordEncoder.encode("qwe123!!"))
                        .nickname(nickname)
                        .provider(ProviderEnum.KAKAO)
                        .build();

                memberRepository.save(kakaoMember);
                br.close();

                return KakaoMemberDto.builder()
                        .nickname(nickname)
                        .email(email)
                        .password(password)
                        .name(name)
                        .isFirst(true)
                        .build();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
