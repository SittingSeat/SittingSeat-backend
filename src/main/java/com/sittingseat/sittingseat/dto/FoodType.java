package com.sittingseat.sittingseat.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodType {
    PREFERENCE("PREFERENCE"), NON_PREFERENCE("NON_PREFERENCE");

    @JsonValue
    private final String foodType;
}
