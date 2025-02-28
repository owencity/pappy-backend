package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ChatMessageDto;
import com.kyu.pappy.dtos.ChatroomDto;
import com.kyu.pappy.services.ChatService;
import com.kyu.pappy.utils.JwtContains;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chats")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;

    }

    @PostMapping
    public ChatroomDto createChatroom(
            @RequestHeader(JwtContains.JWT_HEADER) String token,
            @RequestParam String title
    ) {
        return chatService.createChatroom(token, title);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(
            @RequestHeader(JwtContains.JWT_HEADER) String token,
            @PathVariable Long chatroomId,
            @RequestParam(required = false) Long currentChatroomId) {
        return chatService.joinChatroom(token, chatroomId, currentChatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@RequestHeader(JwtContains.JWT_HEADER) String token, @PathVariable Long chatroomId) {
        return chatService.leaveChatroom(token, chatroomId);
    }

    @GetMapping("/{chatroomId}")
    public ChatroomDto getChatroom(@PathVariable Long chatroomId) {
        return chatService.getChatroom(chatroomId);
    }

    @GetMapping
    public List<ChatroomDto> getChatroomList(@RequestHeader(JwtContains.JWT_HEADER) String token) {
        return chatService.getChatroomList(token);
    }

    @GetMapping("/{chatroomId}/message")
    public List<ChatMessageDto> getMessageList(@PathVariable Long chatroomId) {
        return chatService.getMessageList(chatroomId);
    }
}
