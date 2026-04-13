package com.github.lil69samurai.todo_jwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // JWT anti-counterfeiting key
    private final String SECRET_STRING = "this-is-my-super-secret-key-for-todo-jwt-portfolio-project";
    private final Key key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // Generate a Token (valid for 24 hours)
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
