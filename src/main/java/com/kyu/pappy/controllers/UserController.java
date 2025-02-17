package com.kyu.pappy.controllers;

import com.kyu.pappy.model.user.UserAuthenticationResponse;
import com.kyu.pappy.dtos.UserDto;
import com.kyu.pappy.model.user.UserDeleteRequest;
import com.kyu.pappy.services.UserService;
import com.kyu.pappy.services.UserSignUpService;
import com.kyu.pappy.utils.TokenResponseHelper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserSignUpService userSignUpService;
    private final UserService userService;

    public UserController(UserSignUpService userSignUpService, UserService userService) {
        this.userSignUpService = userSignUpService;
        this.userService = userService;
    }


    @PostMapping("/register")
    public String memberRegister(@Valid @RequestBody UserDto userDto , HttpServletResponse response) {



            UserAuthenticationResponse res =  userSignUpService.registerUser(userDto); // 받은 jwt 객체 , res에 저장
            TokenResponseHelper.addTokensToResponse(response, res);
            // jwt 헤더, 쿠키반환위한 메서드 , 받은 jwt 클라이언트에 헤더(access) 와 쿠키(refresh)로 전달
            return "회원가입 및 로그이 완료";


    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestBody UserDeleteRequest userDeleteRequest, Authentication auth) {
        userService.deleteUser(userDeleteRequest, auth);
    }
}
