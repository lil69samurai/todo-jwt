package com.github.lil69samurai.todo_jwt.controller;

import com.github.lil69samurai.todo_jwt.dto.LoginRequest;
import com.github.lil69samurai.todo_jwt.dto.RegisterRequest;
import com.github.lil69samurai.todo_jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // 這扇門負責接收所有 /api/auth 開頭的網址
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 測試用的小門
    @GetMapping("/ping")
    public String ping() {
        return "Controller Build Success！";
    }

    // 負責接收註冊資料的大門
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            String result = authService.register(request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // New login link
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            // Login success, return JWT Token
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}