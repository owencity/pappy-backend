package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ChatMessageDto;
import com.kyu.pappy.dtos.ChatroomDto;
import com.kyu.pappy.services.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/chats")
@RestController
public class ChatController {

    private final ChatService chatService;
    private static final String JWT_HEADER = "Authorization";

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatroomDto createChatroom(
            @RequestHeader(JWT_HEADER) String token,
            @RequestParam String title
    ) {
        return chatService.createChatroom(token, title);
    }

    @PostMapping("/{chatroomId}")
    public Boolean joinChatroom(
            @RequestHeader(JWT_HEADER) String token,
            @PathVariable Long chatroomId,
            @RequestParam(required = false) Long currentChatroomId) {
        return chatService.joinChatroom(token, chatroomId, currentChatroomId);
    }

    @DeleteMapping("/{chatroomId}")
    public Boolean leaveChatroom(@RequestHeader(JWT_HEADER) String token, @PathVariable Long chatroomId) {
        return chatService.leaveChatroom(token, chatroomId);
    }

    @GetMapping
    public List<ChatroomDto> getChatroomList(@RequestHeader(JWT_HEADER) String token) {
        return chatService.getChatroomList(token);
    }

    @GetMapping("/{chatroomId}/message")
    public List<ChatMessageDto> getMessageList(@PathVariable Long chatroomId) {
        return chatService.getMessageList(chatroomId);
    }
}
