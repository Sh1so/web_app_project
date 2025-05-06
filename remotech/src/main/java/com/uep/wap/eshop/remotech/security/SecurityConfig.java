package com.uep.wap.eshop.remotech.security;

import org.springframework.context.annotation.Bean;
import com.uep.wap.eshop.remotech.security.CustomUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/static/**").permitAll()  // allow static content
        .anyRequest().hasAuthority("admin")
        )
                .formLogin()
                .and()
                .logout();
        return http.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }

@Bean
public AuthenticationManager authManager(HttpSecurity http, CustomUserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder())
        .and()
        .build();
        }
}
