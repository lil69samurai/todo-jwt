package com.github.lil69samurai.todo_jwt.dto;

import lombok.Data;

public class TodoRequest {
    private String title;       // 待辦事項標題
    private String description; // 詳細內容
    private boolean completed;  // 是否已完成 (預設會是 false)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

