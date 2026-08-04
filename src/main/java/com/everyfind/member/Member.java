package com.everyfind.member;

import com.everyfind.school.School;
import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_pw")
    private String password;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    protected Member(){}
    protected Member(String name, String email, String password, School school) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.school = school;
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

    public String getPassword() {
        return password;
    }

    public boolean isEmailVerified(){
        return emailVerified;
    }

    public School getSchool() {
        return school;
    }
}
