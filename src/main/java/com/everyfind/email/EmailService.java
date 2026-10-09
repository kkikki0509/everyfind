package com.everyfind.email;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class EmailService {
    private final Map<String, String> verificationCodes = new HashMap<>();
    private final Set<String> verifiedEmails = new HashSet<>();

    // 이메일 대한 인증번호를 임시 저장
    public void sendVerificationEmail(String email, String code) {
        verificationCodes.put(email, code);

        System.out.println("인증 이메일 발송");
        System.out.println("받는 사람: " + email);
        System.out.println("인증번호: " + code);
    }

    // 인증 번호 생성
    public String createVerificationCode() {
        int number = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(number);
    }

    // 인증 코드 검증
    public boolean verifyCode(String email, String code) {
        if (!code.equals(verificationCodes.get(email))) {
            return false;
        }

        verificationCodes.remove(email);
        verifiedEmails.add(email);

        return true;
    }

    // 이메일 인증 여부
    public boolean isEmailVerified(String email) {
        return verifiedEmails.contains(email);
    }

    // 이메일 인증 후 삭제
    public void removeVerifiedEmail(String email) {
        verifiedEmails.remove(email);
    }
}