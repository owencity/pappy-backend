package com.kyu.pappy.model.request;

import jakarta.validation.constraints.NotEmpty;

public record UserSignUpRequestBody(
        @NotEmpty
        String username,
        @NotEmpty
        String password
) {
}
