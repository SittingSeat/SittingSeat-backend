package com.sittingseat.sittingseat.dto;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import com.sittingseat.sittingseat.enums.ProviderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MemberJoinDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String nickname;
    private ProviderEnum provider;

    private List<FoodCategoryEnum> preferenceFoods;
    private List<FoodCategoryEnum> dislikeFoods;
}
