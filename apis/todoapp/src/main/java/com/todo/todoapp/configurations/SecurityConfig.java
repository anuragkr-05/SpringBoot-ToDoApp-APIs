package com.todo.todoapp.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // Require authentication for all requests
            .formLogin(form -> form.loginPage("/login").permitAll()) // Enable form login and allow everyone to access the login page
            .csrf(csrf -> csrf.disable()) // Optional: disable CSRF for testing or H2 console
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); // Allow frames for H2 console

        return http.build();
    }
}
