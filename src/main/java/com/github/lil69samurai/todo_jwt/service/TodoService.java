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

    // 1. 查詢 (Read)：取得該使用者的所有待辦事項
    public List<Todo> getMyTodos() {
        User currentUser = getCurrentUser();
        return todoRepository.findByUser(currentUser); // 只撈出屬於他的 Todo
    }

    // 2. 新增 (Create)：建立新的待辦事項
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

    // 3. 修改 (Update)：更新待辦事項內容或狀態
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

    // 4. 刪除 (Delete)
    public void deleteTodo(Long id) {
        User currentUser = getCurrentUser();
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("找不到這個待辦事項！"));

        if (!existingTodo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("你沒有權限刪除別人的待辦事項！");
        }

        todoRepository.delete(existingTodo);
    }
}
