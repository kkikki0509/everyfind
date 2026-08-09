package com.everyfind.member;

public class EmailVerificationRequestDto {
    private String email;
    private String code;

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }
}