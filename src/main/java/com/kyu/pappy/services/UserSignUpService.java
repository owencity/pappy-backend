package com.kyu.pappy.services;

import com.kyu.pappy.dtos.MemberDto;
import com.kyu.pappy.entities.Member;
import com.kyu.pappy.repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSignUpService {

    private final MemberRepository memberRepository;

    public UserSignUpService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 회원가입시 cont -> service -> repsoitory -> DB
    // 회원가입시 bcrypt 추가 , 중복 이메일 예외 추가 할것
    public Member registerUser(Member member) {
        return memberRepository.save(member);
    }
}
