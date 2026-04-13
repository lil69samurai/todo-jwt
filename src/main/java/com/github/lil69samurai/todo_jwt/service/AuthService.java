package com.github.lil69samurai.todo_jwt.service;

import com.github.lil69samurai.todo_jwt.dto.LoginRequest;
import com.github.lil69samurai.todo_jwt.dto.RegisterRequest;
import com.github.lil69samurai.todo_jwt.entity.User;
import com.github.lil69samurai.todo_jwt.repository.UserRepository;
import com.github.lil69samurai.todo_jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor // Lombok will automatically inject variables marked as final.
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Add a tool for issuing tokens


    public String register(RegisterRequest request) {
        // Check if the account already exists.
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("This account has already been registered.\n！");
        }

        // Create a new user and encrypt the password.
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // Password encryption
                .role("ROLE_USER") // Preset to grant permissions to general users

                .build();

        // Store in database
        userRepository.save(user);

        return "Registered successfully！";
    }
    // Login function
    public String login(LoginRequest request) {
        // Go to the database to find this account.
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Account can't find！"));

        // Check if the password is correct
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password incorrect！");
        }

        // Issue JWT Tokens
        return jwtUtil.generateToken(user.getUsername());
    }
}
