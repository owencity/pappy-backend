package com.kyu.pappy.enums;

import java.util.Arrays;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String code;
    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Role fromCode(String code)
    {
        return Arrays.stream(Role.values())
                .filter(role -> role.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
