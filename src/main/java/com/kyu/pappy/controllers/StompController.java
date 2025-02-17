package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ChatMessageDto;
import com.kyu.pappy.services.ChatService;
import com.kyu.pappy.services.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class StompController {

    private final ChatService chatService;

    public StompController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chats/{chatroomId}")
    @SendTo("/sub/chats/{chatroomId}") // 구독한 클라이언트에게 메세지 전송
    public ChatMessageDto sendChatMessage(
            SimpMessageHeaderAccessor headerAccessor,
            @DestinationVariable Long chatroomId,
            @Payload Map<String ,String> payload
            ) {
        String nickname = (String) headerAccessor.getSessionAttributes().get("username");
        if(nickname == null) {
            throw new IllegalArgumentException("User not logged in");
        }
        boolean isSaved = chatService.saveMessage(nickname, chatroomId, payload.get("message"));
        if(!isSaved) {
            throw new RuntimeException("Message save failed");
        }

        //        messagingTemplate.convertAndSend("/sub/chats/{chatroomId}", payload.get("message")); // @SendTO 어노테이션으로 대체가능
        return new ChatMessageDto(nickname, payload.get("message"));
    }
}
