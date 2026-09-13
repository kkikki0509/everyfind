package com.everyfind.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // URL 접근 권한
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/members/**", "/error")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                ).formLogin(form -> form // 로그인 방식 설정
                        .loginPage("/members/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/members/login?success", true)
                        .failureUrl("/members/login?error")
                        .permitAll()
                );

        return http.build();
    }
}