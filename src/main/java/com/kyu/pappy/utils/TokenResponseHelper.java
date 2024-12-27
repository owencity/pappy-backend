package com.kyu.pappy.utils;

import com.kyu.pappy.model.user.UserAuthenticationResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class TokenResponseHelper {

    public static void addTokensToResponse(HttpServletResponse response, UserAuthenticationResponse token) {

        response.setHeader("Authorization", "Bearer " + token.accessToken());
        // refresh token 쿠키로 설정
        response.addCookie(createCookie(token.refreshToken()));
    }

    private static Cookie createCookie(String value) {
        Cookie refreshCookie = new Cookie("refreshToken", value);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 2일 유효

        return refreshCookie;
    }

}
