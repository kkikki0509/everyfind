package com.everyfind.email;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class EmailService {
    private final Map<String, String> verificationCodes = new HashMap<>();

    public void sendVerificationEmail(String email, String code) {
        verificationCodes.put(email, code);

        System.out.println("인증 이메일 발송");
        System.out.println("받는 사람: " + email);
        System.out.println("인증번호: " + code);
    }

    public String createVerificationCode() {
        int number = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(number);
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }

}