package com.everyfind.member;

import com.everyfind.school.School;
import com.everyfind.school.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, SchoolRepository schoolRepository) {
        this.memberRepository = memberRepository;
        this.schoolRepository = schoolRepository;
    }

    public Member createMember(MemberRequestDto requestDto) {
        String email = requestDto.getEmail();
        String domain = email.substring(email.indexOf("@") + 1);
        Optional<School> optSchool = schoolRepository.findByDomain(domain);
        School school = null;

        // 이메일 가입 여부
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 학교 도메인 검색
        try{
            school = optSchool.get();
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException("해당 학교 도메인을 찾을 수 없습니다.");
        }

        // 해시 암호화(salt + key stretching 알고리즘 포함)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(requestDto.getPassword());

        Member member = new Member(
                requestDto.getName(),
                requestDto.getEmail(),
                encodedPassword,
                school
        );

        return memberRepository.save(member);
    }
}