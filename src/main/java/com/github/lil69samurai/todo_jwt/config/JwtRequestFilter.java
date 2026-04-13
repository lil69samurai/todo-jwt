package com.github.lil69samurai.todo_jwt.config;

import com.github.lil69samurai.todo_jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extract the "Authorization" field from the request header.
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the token exists and if it begins with "Bearer"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Cut off "Bearer", leaving only the garbled text that follows.
            try {
                username = jwtUtil.extractUsername(jwt); // Verify and obtain account
            } catch (Exception e) {
                System.out.println("Token verification failed or expired.\n！");
            }
        }

        // If the account is resolved and the system is not currently logged in, create a new account for them and add it to the system.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, new ArrayList<>()); // 建立 Spring Security 認可的通行證

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken); // Guards will let you through!
        }

        // Check is complete, pass the request to the next person or the Controller).
        chain.doFilter(request, response);
    }
}
