package com.kyu.pappy.controllers;

import com.kyu.pappy.model.user.UserAuthenticationResponse;
import com.kyu.pappy.model.user.UserDto;
import com.kyu.pappy.services.UserSignUpService;
import com.kyu.pappy.utils.TokenResponseHelper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserSignUpService userSignUpService;

    public UserController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> memberRegister(@Valid @RequestBody UserDto userDto , HttpServletResponse response) {
        try {
            UserAuthenticationResponse res =  userSignUpService.registerUser(userDto); // 받은 jwt 객체 , res에 저장
            TokenResponseHelper.addTokensToResponse(response, res);
            // jwt 헤더, 쿠키반환위한 메서드 , 받은 jwt 클라이언트에 헤더(access) 와 쿠키(refresh)로 전달

            return ResponseEntity.ok("회원가입 및 로그인 완료");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }
}
