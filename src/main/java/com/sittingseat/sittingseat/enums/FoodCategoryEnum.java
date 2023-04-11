package com.sittingseat.sittingseat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodCategoryEnum {
    KOREAN_FOOD("KOREAN_FOOD"), CHINESE_FOOD("CHINESE_FOOD"), JAPANESE_FOOD("JAPANESE_FOOD");

    private final String foodCategory;
}
