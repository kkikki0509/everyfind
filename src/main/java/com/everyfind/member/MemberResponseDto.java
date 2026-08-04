package com.everyfind.member;

public class MemberResponseDto {

    private Long id;
    private String name;
    private String email;
    private boolean emailVerified;
    private String schoolName;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.emailVerified = member.isEmailVerified();
        this.schoolName = member.getSchool().getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getSchoolName() {
        return schoolName;
    }
}