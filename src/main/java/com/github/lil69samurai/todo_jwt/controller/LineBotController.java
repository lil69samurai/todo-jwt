package com.github.lil69samurai.todo_jwt.controller;

import com.github.lil69samurai.todo_jwt.entity.Todo;
import com.github.lil69samurai.todo_jwt.service.TodoService;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@LineMessageHandler
@RequiredArgsConstructor
public class LineBotController {

    private final TodoService todoService;
    private final List<String> tempTodoList = new ArrayList<>(List.of("買晚餐", "面試準備"));


    @EventMapping
    public Message handleTextMessageEvent(MessageEvent event) {
        if (event.message() instanceof TextMessageContent) {
            TextMessageContent messageContent = (TextMessageContent) event.message();
            String userText = messageContent.text();
            String replyText = "";

            String lineUserId = event.source().userId();
            try {
                // 邏輯 1：綁定帳號
                if (userText.startsWith("綁定帳號：")) {
                    String username = userText.replace("綁定帳號：", "").trim();
                    replyText = todoService.bindLineAccount(username, lineUserId);

                    // 邏輯 2：新增待辦
                } else if (userText.startsWith("新增待辦：")) {
                    String todoTitle = userText.replace("新增待辦：", "").trim();
                    todoService.createTodoByLineId(lineUserId, todoTitle);
                    replyText = "✅ 成功幫您新增待辦：「" + todoTitle + "」";

                    // 邏輯 3：查詢待辦
                } else if (userText.equals("今日待辦")) {
                    List<Todo> todos = todoService.getTodosByLineId(lineUserId);
                    if (todos.isEmpty()) {
                        replyText = "📭 您目前沒有任何待辦事項喔！";
                    } else {
                        StringBuilder sb = new StringBuilder("📋 您的今日待辦有：\n");
                        for (int i = 0; i < todos.size(); i++) {
                            sb.append(i + 1).append(". ").append(todos.get(i).getTitle());
                            if (todos.get(i).isCompleted()) {
                                sb.append(" (✅已完成)"); // 如果完成會打勾
                            }
                            sb.append("\n");
                        }
                        replyText = sb.toString().trim();
                    }

                } else {
                    replyText = "對不起，我只聽得懂：\n1. 綁定帳號：您的帳號\n2. 今日待辦\n3. 新增待辦：xxx";
                }

            } catch (Exception e) {
                // 如果發生錯誤（例如還沒綁定就想查詢），就把錯誤訊息傳給他
                replyText = "⚠️ 發生錯誤：" + e.getMessage();
            }

            return new TextMessage(replyText);
        }
        return null;
    }
}