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
    private final PasswordEncoder   encoder;
    private final EmailService emailService;

    @Autowired
    public MemberService(MemberRepository memberRepository, SchoolRepository schoolRepository,
                         PasswordEncoder encoder, EmailService emailService) {
        this.memberRepository = memberRepository;
        this.schoolRepository = schoolRepository;
        this.encoder = encoder;
        this.emailService = emailService;
    }

    // 회원가입
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
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("해당 학교 도메인을 찾을 수 없습니다.");
        }

        // 해시 암호화(salt + key stretching 알고리즘 포함)
        String encodedPassword = encoder.encode(requestDto.getPassword());

        Member member = new Member(
                requestDto.getName(),
                requestDto.getEmail(),
                encodedPassword,
                school
        );

        return memberRepository.save(member);
    }

    // 로그인
    public Member login(LoginRequestDto requestDto) {

        Optional<Member> optMember = memberRepository.findByEmail(requestDto.getEmail());
        Member member = null;

        try{
            member = optMember.get();
        }
        catch(NoSuchElementException e){
            throw new NoSuchElementException("존재하지 않는 이메일입니다.");
        }

        if (!encoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return member;
    }

    // 이메일 인증
    public void sendVerificationCode(String email) {
        String code = emailService.createVerificationCode();
        emailService.sendVerificationEmail(email, code);
    }

    // 이메일 인증 검증
    public boolean verifyEmail(EmailVerificationRequestDto requestDto) {
        String email = requestDto.getEmail();
        Optional<Member> optMember = memberRepository.findByEmail(requestDto.getEmail());
        Member member = null;

        if (!emailService.verifyCode(requestDto.getEmail(), requestDto.getCode())) {
            throw new IllegalArgumentException("인증번호가 틀렸습니다.");
        }

        try{
            member = optMember.get();
        }
        catch(NoSuchElementException e){
            throw new NoSuchElementException("존재하지 않는 이메일입니다.");
        }

        member.verifyEmail(); // emailVerified 멤버 속성을 true로 변환
        memberRepository.save(member);

        return true;
    }
}