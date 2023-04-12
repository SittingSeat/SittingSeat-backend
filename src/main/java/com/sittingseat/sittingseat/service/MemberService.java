package com.sittingseat.sittingseat.service;

import com.sittingseat.sittingseat.domain.Food;
import com.sittingseat.sittingseat.domain.Member;
import com.sittingseat.sittingseat.domain.PreferenceFood;
import com.sittingseat.sittingseat.dto.FoodType;
import com.sittingseat.sittingseat.dto.MemberJoinDto;
import com.sittingseat.sittingseat.repository.FoodRepository;
import com.sittingseat.sittingseat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;


    @Transactional
    public void join(MemberJoinDto memberJoinDto){
        Member joinMember = Member.builder()
                .name(memberJoinDto.getName())
                .email(memberJoinDto.getEmail())
                .password(memberJoinDto.getPassword())
                .phone(memberJoinDto.getPhone())
                .nickname(memberJoinDto.getNickname())
                .provider(memberJoinDto.getProvider())
                .build();
        memberRepository.save(joinMember);

        memberJoinDto.getPreferenceFoods()
                .forEach(pf -> {
                    Food preferenceFood = Food.builder()
                            .member(joinMember)
                            .food(pf)
                            .foodType(FoodType.PREFERENCE)
                            .build();
                    foodRepository.save(preferenceFood);
                });

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
