package com.kyu.pappy.controllers;

import com.kyu.pappy.model.user.UserDto;
import com.kyu.pappy.services.UserSignUpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class UserController {

    private final UserSignUpService userSignUpService;

    public UserController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> memberRegister(@Valid @RequestBody UserDto userDto) {
        try {
            userSignUpService.registerUser(userDto);
            return ResponseEntity.ok("회원가입성공");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }
}
