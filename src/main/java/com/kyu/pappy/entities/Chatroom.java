package com.kyu.pappy.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    String title;

    @OneToMany(mappedBy = "chatroom", fetch = FetchType.LAZY)
    Set<ChatroomMapping> chatroomMappingSet;

    LocalDateTime createAt;

    @Transient
    private Boolean hasNewMessage;

    public void setHasNewMessage(Boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    // 채팅방에 새로운 멤버 추가, 매핑 정보 반환
    public ChatroomMapping addMember(User user) {
        if(this.getChatroomMappingSet() == null) {
            this.chatroomMappingSet = new HashSet<>();
        }

        ChatroomMapping chatroomMapping = ChatroomMapping.builder()
                .user(user)
                .chatroom(this)
                .build();

        this.chatroomMappingSet.add(chatroomMapping);
        return chatroomMapping;

    }
}
