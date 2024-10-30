package org.lancoo.crm.controller;

import org.lancoo.crm.domain.ChatMessage;
import org.lancoo.crm.domain.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    /**
     * 用于处理聊天消息,并将其广播到"/topic/messages"主题。
     *
     * @param message
     * @return
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleChatMessage(ChatMessage message) {
        // 对消息进行处理并广播给订阅了"/topic/messages"的客户端
        return message;
    }

    /**
     * 用于处理通知消息,并将其推送到当前用户的"/queue/notifications"队列。
     *
     * @param notification
     * @return
     */
    @MessageMapping("/notify")
    @SendToUser("/queue/notifications")
    public Notification handleNotification(Notification notification) {
        // 对通知消息进行处理并推送给当前用户
        return notification;
    }
}