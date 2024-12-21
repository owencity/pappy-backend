package com.kyu.pappy.services;

import com.kyu.pappy.dtos.UserDto;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.exceptions.user.UserAlreadyExistException;
import com.kyu.pappy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    // 회원가입시 cont -> service -> repsoitory -> DB
    // 회원가입시 bcrypt 추가(시큐리티 추후 JWT적용시 같이 적용) , 중복 이메일 예외 추가 할것
    public void registerUser(UserDto userDto) {
        String userEmail = userDto.userEmail();
        userRepository
                .findByUserEmail(userEmail)
                .ifPresent(user -> {
                    throw new UserAlreadyExistException();
                });

        User user =  User.builder()
                .userEmail(userDto.userEmail())
                .username(userDto.username())
                .password(passwordEncoder.encode(userDto.password())) // 암호화된 비밀번호 설정
                .role(userDto.role())
                .gender(userDto.gender())
                .createdAt(new Date())
                .build();

        userRepository.save(user);
    }
}
