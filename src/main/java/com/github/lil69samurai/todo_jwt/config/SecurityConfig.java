package com.github.lil69samurai.todo_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityConfig() {
        // 如果系統有讀到這個檔案，啟動時 Console 會印出這行字！
        System.out.println("====== SecurityConfig 設定檔已經成功載入！ ======");
    }
    // Password Encryptor
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Safety filter chain settings
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disabling CSRF protection.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow URLs access data without logging in (for registration/login).
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated() // All other websites require login credentials to access.
                );
        return http.build();
    }
}
