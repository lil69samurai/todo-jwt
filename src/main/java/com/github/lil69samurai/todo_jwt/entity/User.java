package com.github.lil69samurai.todo_jwt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // users List
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Safe uder LINE ID
    @Column(name = "line_user_id", unique = true)
    private String lineUserId;

    @Column(nullable = false)
    private String role; // 例如: ROLE_USER
}
