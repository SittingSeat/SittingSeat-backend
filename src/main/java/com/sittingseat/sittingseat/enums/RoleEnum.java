package com.sittingseat.sittingseat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER("USER"), STORE("STORE"), ADMIN("ADMIN");

    @JsonValue
    private final String role;
}
