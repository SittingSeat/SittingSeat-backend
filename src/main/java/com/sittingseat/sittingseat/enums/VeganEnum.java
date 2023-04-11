package com.sittingseat.sittingseat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VeganEnum {
    VEGAN("VEGAN"), NON_VEGAN("NON_VEGAN");

    @JsonValue
    private final String vegan;

}
