package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Member;
import com.kyu.pappy.enums.Gender;
import com.kyu.pappy.enums.Role;

import java.util.Date;

public record MemberDto(
        Long id,
        String userEmail,
        String username,
        Role role,
        Gender gender,
        Date createdAt
) {
    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUserEmail(),
                member.getUsername(),
                member.getRole(),
                member.getGender(),
                member.getCreatedAt()
                );
    }

    public static Member to(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.id())
                .userEmail(memberDto.userEmail())
                .username(memberDto.username())
                .role(memberDto.role())
                .gender(memberDto.gender())
                .createdAt(memberDto.createdAt())
                .build();
    }
}
