package com.sittingseat.sittingseat.dto;

import com.sittingseat.sittingseat.enums.FoodCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberJoinDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String nickname;
    private String provider;

    private List<FoodCategoryEnum> preferenceFoods;
    private List<FoodCategoryEnum> dislikeFoods;
}
