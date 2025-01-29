package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ChatMessageDto;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.services.ChatService;
import com.kyu.pappy.services.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.Optional;

@Controller
public class StompController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final UserService userService;

    public StompController(SimpMessagingTemplate messagingTemplate, ChatService chatService, UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.userService = userService;
    }

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/sub/chats/{chatroomId}")
    public ChatMessageDto ChatMessage(
            SimpMessageHeaderAccessor headerAccessor,
            @DestinationVariable Long chatroomId,
            @Payload Map<String ,String> payload
            ) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username == null) {
            throw new IllegalArgumentException("User not logged in");
        }

        chatService.saveMessage(username, chatroomId, payload.get("message"));
        messagingTemplate.convertAndSend("/sub/chats/{chatroomId}", payload.get("message"));
        return new ChatMessageDto(username, payload.get("message"));
    }
}
