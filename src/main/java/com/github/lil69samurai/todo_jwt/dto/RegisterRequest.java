package com.github.lil69samurai.todo_jwt.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}

