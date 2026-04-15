package com.github.lil69samurai.todo_jwt.controller;

import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

import java.util.ArrayList;
import java.util.List;


@LineMessageHandler
public class LineBotController {

    private final List<String> tempTodoList = new ArrayList<>(List.of("買晚餐", "面試準備"));


    @EventMapping
    public Message handleTextMessageEvent(MessageEvent event) {
        if (event.message() instanceof TextMessageContent) {
            TextMessageContent messageContent = (TextMessageContent) event.message();
            String userText = messageContent.text();
            String replyText = "";

        if (userText.startsWith("新增待辦：")) {
            // 1. 擷取待辦內容
            String todoContent = userText.replace("新增待辦：", "").trim();

            // 2. 🌟 把它加進我們的清單裡！
            tempTodoList.add(todoContent);

            replyText = "✅ 成功幫您新增待辦：「" + todoContent + "」";

        } else if (userText.equals("今日待辦")) {
            // 🌟 動態把清單裡的項目組合起來
            if (tempTodoList.isEmpty()) {
                replyText = "📭 您今天沒有任何待辦事項喔！";
            } else {
                StringBuilder sb = new StringBuilder("📋 您的今日待辦有：\n");
                for (int i = 0; i < tempTodoList.size(); i++) {
                    sb.append(i + 1).append(". ").append(tempTodoList.get(i)).append("\n");
                }
                replyText = sb.toString().trim();
            }

        }else {
                replyText = "對不起，我只聽得懂「今日待辦」或「新增待辦：xxx」哦！😅";
            }

            return new TextMessage(replyText);
        }
        return null;
    }
}