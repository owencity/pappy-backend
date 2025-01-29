package com.kyu.pappy.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String text;
    private LocalDateTime createAt;
    private String nickname;
    private Long chatroomId;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "chatroom_id")
    @ManyToOne
    private Chatroom chatroom;

}

