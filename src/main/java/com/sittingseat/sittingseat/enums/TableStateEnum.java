package com.sittingseat.sittingseat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TableStateEnum {
    FREE("FREE"), RESERVATION("RESERVATION");

    @JsonValue
    private final String tableState;
}
