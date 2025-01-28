package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ChatroomDto;
import com.kyu.pappy.security.JwtUtil;
import com.kyu.pappy.services.ChatService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chats")
@RestController
public class ChatController {

    private final ChatService chatService;
    private final JwtUtil jwtUtil;

    public ChatController(ChatService chatService, JwtUtil jwtUtil) {
        this.chatService = chatService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ChatroomDto createChatroom(
            @RequestHeader("Authorization") String token,
            @RequestParam String title
    ) {
        return chatService.createChatroom(token, title);
    }
}
