package com.kyu.pappy.services;

import com.kyu.pappy.model.user.UserDto;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.config.exceptions.user.UserAlreadyExistException;
import com.kyu.pappy.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserSignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserSignUpService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // 회원가입시 bcrypt 추가(시큐리티 추후 JWT적용시 같이 적용) , 중복 이메일 예외 추가 할것
    // 회원가입 완료시 JWT 발급 하면서 로그인 고려
    public void registerUser(UserDto userDto) {

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
    }

    // 비밀번호 유효성 검사 이후 Bcrypt로 변환
    private String validateAndEncodePassword(String password, String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
        return passwordEncoder.encode(password);
    }
}
