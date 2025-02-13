package com.kyu.pappy.services;

import com.kyu.pappy.model.user.UserAuthenticationResponse;
import com.kyu.pappy.dtos.UserDto;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.config.exceptions.user.UserAlreadyExistException;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public UserSignUpService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }


    // 회원가입 완료시 JWT 발급 하면서 로그인 고려
    public UserAuthenticationResponse registerUser(UserDto userDto) {

        if(userDto.userEmail() == null || userDto.password() == null) {
            throw new IllegalArgumentException("이메일과 비밀번호는 필수 입력입니다.");
        }
        // Email(아이디) 중복확인
        String userEmail = userDto.userEmail();
        userRepository
                .findByUserEmail(userEmail)
                .ifPresent(user -> {
                    throw new UserAlreadyExistException(userEmail);
                });

        // 비밀번호 비교 확인후 encode
        String encodedPassword = validateAndEncodePassword(userDto.password(), userDto.confirmedPassword());

        // 비밀번호 확인 이후 DTO -> 엔티티로 변환 DB저장
        User user = UserDto.to(userDto, encodedPassword);
        userRepository.save(user);

        // 회원가입 후 자동 로그인 위한 JWT 발급
        String accessToken =  jwtUtil.createJwt("access", userEmail, String.valueOf(user.getRole()), 600000L);
        String refreshToken = jwtUtil.createJwt("refresh", userEmail, String.valueOf(user.getRole()), 86400000L);

        // JWT 토큰을 dto 객체로 반환
        return new UserAuthenticationResponse(accessToken, refreshToken);
    }

    // 비밀번호 유효성 검사 이후 Bcrypt로 변환
    private String validateAndEncodePassword(String password, String confirmedPassword) {

        // 회원가입 두개의 비밀번호 유효성 검사
        if (!password.equals(confirmedPassword)) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        // 일치할시 encode 하여 return
        return bCryptPasswordEncoder.encode(password);
    }
}
