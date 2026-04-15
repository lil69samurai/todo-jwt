package com.github.lil69samurai.todo_jwt.service;

import com.github.lil69samurai.todo_jwt.dto.TodoRequest;
import com.github.lil69samurai.todo_jwt.entity.Todo;
import com.github.lil69samurai.todo_jwt.entity.User;
import com.github.lil69samurai.todo_jwt.repository.TodoRepository;
import com.github.lil69samurai.todo_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // 💡 秘密武器：從 Spring Security 保險箱拿出「目前登入的使用者」
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Currently logged-in users cannot be found！"));
    }

    // Search user's all To-do list.
    public List<Todo> getMyTodos() {
        User currentUser = getCurrentUser();
        return todoRepository.findByUser(currentUser); // 只撈出屬於他的 Todo
    }

    // Create new To-do status.
    public Todo createTodo(TodoRequest request) {
        User currentUser = getCurrentUser();

        Todo newTodo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(false) // 剛建立預設是未完成
                .user(currentUser) // 核心：綁定給目前登入的使用者！
                .build();

        return todoRepository.save(newTodo);
    }

    // Update To-do status.
    public Todo updateTodo(Long id, TodoRequest request) {
        User currentUser = getCurrentUser();
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("找不到這個待辦事項！"));

        // 安全防護：確定這個 Todo 真的是這個人的，防駭客亂改別人的
        if (!existingTodo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("你沒有權限修改別人的待辦事項！");
        }

        existingTodo.setTitle(request.getTitle());
        existingTodo.setDescription(request.getDescription());
        existingTodo.setCompleted(request.isCompleted());

        return todoRepository.save(existingTodo);
    }

    // Delete
    public void deleteTodo(Long id) {
        User currentUser = getCurrentUser();
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("找不到這個待辦事項！"));

        if (!existingTodo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("你沒有權限刪除別人的待辦事項！");
        }

        todoRepository.delete(existingTodo);
    }
    // ==========================================
    // 🌟 以下為 LINE Bot 專屬方法 (繞過 JWT，改認 LINE ID)
    // ==========================================

    // LINE 功能 1：綁定帳號 (把使用者的 username 和 lineUserId 連在一起)
    public String bindLineAccount(String username, String lineUserId) {
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return "找不到此帳號，請確認帳號名稱是否正確！";
        }

        user.setLineUserId(lineUserId);
        userRepository.save(user);
        return "🔗 綁定成功！您現在可以開始使用 LINE 管理待辦事項了。";
    }

    // LINE 功能 2：透過 LINE ID 查詢待辦事項
    public List<Todo> getTodosByLineId(String lineUserId) {
        User user = userRepository.findByLineUserId(lineUserId)
                .orElseThrow(() -> new RuntimeException("請先輸入「綁定帳號：您的帳號」進行綁定！"));

        return todoRepository.findByUser(user);
    }

    // LINE 功能 3：透過 LINE ID 新增待辦事項
    public Todo createTodoByLineId(String lineUserId, String title) {
        User user = userRepository.findByLineUserId(lineUserId)
                .orElseThrow(() -> new RuntimeException("請先輸入「綁定帳號：您的帳號」進行綁定！"));

        Todo newTodo = Todo.builder()
                .title(title)
                .description("來自 LINE 的待辦事項")
                .completed(false)
                .user(user)
                .build();

        return todoRepository.save(newTodo);
    }
}
