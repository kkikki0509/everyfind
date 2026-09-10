package com.everyfind.member;

import com.everyfind.email.EmailService;
import com.everyfind.school.School;
import com.everyfind.school.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;

    @Autowired
    public MemberService(MemberRepository memberRepository, SchoolRepository schoolRepository,
                         PasswordEncoder encoder, EmailService emailService) {
        this.memberRepository = memberRepository;
        this.schoolRepository = schoolRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    /* 회원가입 */
    public Member createMember(MemberRequestDto requestDto) {
        String email = requestDto.getEmail();
        String domain = email.substring(email.indexOf("@") + 1);

        // 도메인 유무 검사
        School school = schoolRepository.findByDomain(domain).orElseThrow(() ->
                new NoSuchElementException("해당 학교 도메인을 찾을 수 없습니다."));

        // 이메일 가입 여부
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 해시 적용
        String encodedPassword = encoder.encode(requestDto.getPassword());

        // 회원 생성
        Member member = new Member(requestDto.getName(), requestDto.getEmail(), encodedPassword, school);

        // DB 저장
        return memberRepository.save(member);
    }

    /* 로그인 */
    public Member login(LoginRequestDto requestDto) {
        // 가입 이메일 유무
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(() ->
                new NoSuchElementException("가입되지 않은 이메일입니다."));

        // 비밀번호 검증(입력창 및 DB)
        if (!encoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        return member;
    }

    /* 이메일 인증 번호 */
    public void sendVerificationCode(String email) {
        String code = emailService.createVerificationCode();
        emailService.sendVerificationEmail(email, code);
    }

    /* 이메일 인증 번호 검증 */
    public boolean verifyEmail(EmailVerificationRequestDto requestDto) {
        if (!emailService.verifyCode(requestDto.getEmail(), requestDto.getCode())) {
            throw new IllegalArgumentException("인증번호가 올바르지 않습니다.");
        }

        return true;
    }
}