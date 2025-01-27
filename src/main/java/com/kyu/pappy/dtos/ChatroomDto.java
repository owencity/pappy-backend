package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Chatroom;

import java.time.LocalDateTime;

public record ChatroomDto(
        Long id,
        String title,
        Boolean hasNewMessage,
        Integer userCount,
        LocalDateTime createAt
) {
    public static ChatroomDto from(Chatroom chatroom) {
        return new ChatroomDto(
                chatroom.getId(),
                chatroom.getTitle(),
                chatroom.getHasNewMessage(),
                chatroom.getChatroomMappingSet().size(),
                chatroom.getCreateAt()
        );
    }
}
