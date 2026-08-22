package com.everyfind.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberPageController {

    @GetMapping("/members/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/members/signup")
    public String signupPage() {
        return "signup";
    }
}