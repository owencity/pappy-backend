package com.kyu.pappy.dtos;


import com.kyu.pappy.entities.Message;

public record ChatMessageDto(
        String sender,
        String message
) {
    public static ChatMessageDto from(Message message) {
        return new ChatMessageDto(
                message.getNickname(),
                message.getText()
        );
    }
}
