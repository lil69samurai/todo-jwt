package com.github.lil69samurai.todo_jwt.controller;

import com.github.lil69samurai.todo_jwt.dto.TodoRequest;
import com.github.lil69samurai.todo_jwt.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos") // Ｈandles all URLs starting with /api/todos.
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    // View all my to-do items (GET http://localhost:8080/api/todos)
    @GetMapping
    public ResponseEntity<?> getMyTodos() {
        return ResponseEntity.ok(todoService.getMyTodos());
    }

    // Add a to-do item (POST http://localhost:8080/api/todos)
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoRequest request) {
        try {
            return ResponseEntity.ok(todoService.createTodo(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Modify to-do list (PUT http://localhost:8080/api/todos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody TodoRequest request) {
        try {
            return ResponseEntity.ok(todoService.updateTodo(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete to-do list (DELETE http://localhost:8080/api/todos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.ok("Successful deleted！");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
