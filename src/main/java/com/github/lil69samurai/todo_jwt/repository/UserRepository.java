package com.github.lil69samurai.todo_jwt.repository;

import com.github.lil69samurai.todo_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 讓系統可以透過帳號來尋找使用者 (登入或檢查重複註冊時會用到)
    Optional<User> findByUsername(String username);
}