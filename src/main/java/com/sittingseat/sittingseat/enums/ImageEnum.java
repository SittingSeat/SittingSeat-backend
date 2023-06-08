package com.sittingseat.sittingseat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageEnum {
    MENU("MENU"), INTERIOR("INTERIOR");

    @JsonValue
    private String image;
}
