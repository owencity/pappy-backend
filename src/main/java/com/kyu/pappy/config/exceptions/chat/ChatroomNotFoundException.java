package com.kyu.pappy.config.exceptions.chat;

import com.kyu.pappy.config.exceptions.ClientErrorException;
import org.springframework.http.HttpStatus;

public class ChatroomNotFoundException extends ClientErrorException {

    public ChatroomNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Chatroom not found");
    }

    public ChatroomNotFoundException(Long chatroomId) {
        super(HttpStatus.NOT_FOUND, "Chatroom not found" + chatroomId);
    }
}
