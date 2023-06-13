package com.sittingseat.sittingseat.service;

import com.sittingseat.sittingseat.domain.Food;
import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.dto.FoodType;
import com.sittingseat.sittingseat.dto.MemberJoinDto;
import com.sittingseat.sittingseat.dto.TokenInfo;
import com.sittingseat.sittingseat.exception.SittingSeatErrorCode;
import com.sittingseat.sittingseat.exception.SittingSeatException;
import com.sittingseat.sittingseat.repository.FoodRepository;
import com.sittingseat.sittingseat.repository.MemberRepository;
import com.sittingseat.sittingseat.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public void join(MemberJoinDto memberJoinDto){
        Member joinMember = Member.builder()
                .name(memberJoinDto.getName())
                .email(memberJoinDto.getEmail())
                .password(passwordEncoder.encode(memberJoinDto.getPassword()))
                .phone(memberJoinDto.getPhone())
                .nickname(memberJoinDto.getNickname())
                .provider(memberJoinDto.getProvider())
                .build();

        memberRepository.save(joinMember);


        if(!memberJoinDto.getPreferenceFoods().isEmpty()){
            memberJoinDto.getPreferenceFoods()
                    .forEach(pf -> {
                        Food preferenceFood = Food.builder()
                                .member(joinMember)
                                .food(pf)
                                .foodType(FoodType.PREFERENCE)
                                .build();
                        foodRepository.save(preferenceFood);
                    });
        }
        if(!memberJoinDto.getDislikeFoods().isEmpty()){
            memberJoinDto.getDislikeFoods()
                    .forEach(df -> {
                        Food nonPreferenceFood = Food.builder()
                                .member(joinMember)
                                .food(df)
                                .foodType(FoodType.NON_PREFERENCE)
                                .build();
                        foodRepository.save(nonPreferenceFood);
                    });
        }

    }

    public TokenInfo login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email)
                .orElseThrow(()-> new SittingSeatException(SittingSeatErrorCode.MEMBER_NOT_FOUND));
    }

}
