package com.github.lil69samurai.todo_jwt.repository;

import com.github.lil69samurai.todo_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Through account to search user (登入或檢查重複註冊時會用到)
    Optional<User> findByUsername(String username);

    // Through LINE ID to search user
    Optional<User> findByLineUserId(String lineUserId);

    // Through email to search user (用來做綁定)
    //Optional<User> findByEmail(String email);
}