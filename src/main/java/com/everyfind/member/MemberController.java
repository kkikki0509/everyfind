package com.everyfind.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("/members")
    public MemberResponseDto SignUp(@RequestBody MemberRequestDto requestDto) {
        Member member = memberService.createMember(requestDto);
        return new MemberResponseDto(member);
    }

    // 로그인
    @PostMapping("/login")
    public Member login(@RequestBody LoginRequestDto requestDto) {
        return memberService.login(requestDto);
    }

    // 이메일 인증
    @PostMapping("/members/email")
    public void sendVerificationEmail(@RequestBody EmailVerificationRequestDto requestDto) {
        memberService.sendVerificationCode(requestDto.getEmail());
    }

    // 이메일 인증 검증
    @PostMapping("/members/email/verify")
    public boolean verifyEmail(@RequestBody EmailVerificationRequestDto requestDto) {
        return memberService.verifyEmail(requestDto);
    }
}
