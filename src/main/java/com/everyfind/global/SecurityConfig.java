package com.everyfind.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/members/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                ).formLogin(form -> form
                        .loginPage("/members/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/members/login?success", true)
                        .failureUrl("/members/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/members/logout")
                        .logoutSuccessUrl("/members/login")
                );

        return http.build();
    }
}