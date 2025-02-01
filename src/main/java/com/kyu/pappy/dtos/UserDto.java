package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.User;
import com.kyu.pappy.enums.Gender;
import com.kyu.pappy.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record UserDto(
        @NotBlank(message = "이메일은 필수값입니다.")
        @Email(message = "유요한 이메일 값이 아닙니다.")
        String userEmail,
        String nickname,
        String password,
        String confirmedPassword,
        @NotBlank(message = "사용자 입력값은 필수값입니다.")
        String username,
        Role role,
        Gender gender,
        Date createdAt
) {

    // DB에서 굳이 Dto로 비밀번호 가져올 이유없음 null
    public static UserDto from(User user) {
        return new UserDto(
                user.getUserEmail(),
                user.getUserEmail(),
                null,
                null,
                user.getUsername(),
                user.getRole(),
                user.getGender(),
                user.getCreatedAt()
                );
    }

    public static User to(UserDto userDto, String encodedPassword) {
        return User.builder()
                .userEmail(userDto.userEmail())
                .password(encodedPassword)
                .username(userDto.username())
                .nickname(userDto.nickname())
                .role(userDto.role())
                .gender(userDto.gender())
                .createdAt(new Date())
                .build();
    }
}
