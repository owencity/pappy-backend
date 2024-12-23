package com.kyu.pappy.model.user;

public record UserAuthenticationResponse(
        String accessToken,
        String refreshToken
) {
}
