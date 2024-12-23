package com.kyu.pappy.model.user;

import com.kyu.pappy.entities.User;
import com.kyu.pappy.enums.Gender;
import com.kyu.pappy.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record UserDto(
        Long id,
        @NotBlank(message = "이메일은 필수값입니다.")
        @Email(message = "유요한 이메일 값이 아닙니다.")
        String userEmail,
        String password,
        @NotBlank(message = "사용자 입력값은 필수값입니다.")
        String username,
        Role role,
        Gender gender,
        Date createdAt
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUserEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getGender(),
                user.getCreatedAt()
                );
    }

    public static User to(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .userEmail(userDto.userEmail())
                .password(userDto.password())
                .username(userDto.username())
                .role(userDto.role())
                .gender(userDto.gender())
                .createdAt(userDto.createdAt())
                .build();
    }
}
