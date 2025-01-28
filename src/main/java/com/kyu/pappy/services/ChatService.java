package com.kyu.pappy.services;

import com.kyu.pappy.dtos.ChatroomDto;
import com.kyu.pappy.entities.Chatroom;
import com.kyu.pappy.entities.ChatroomMapping;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.repositories.ChatroomMappingRepository;
import com.kyu.pappy.repositories.ChatroomRepository;
import com.kyu.pappy.repositories.MessageRepository;
import com.kyu.pappy.repositories.UserRepository;
import com.kyu.pappy.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatroomRepository chatroomRepository;
    private final ChatroomMappingRepository chatroomMappingRepository;
    private final MessageRepository messageRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public ChatService(ChatroomRepository chatroomRepository, ChatroomMappingRepository chatroomMappingRepository, MessageRepository messageRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.chatroomRepository = chatroomRepository;
        this.chatroomMappingRepository = chatroomMappingRepository;
        this.messageRepository = messageRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public ChatroomDto createChatroom(String token, String title) {
        Chatroom chatroom = Chatroom.builder()
                .title(title)
                .createAt(LocalDateTime.now())
                .build();
        ChatroomDto createChatroom = ChatroomDto.from(chatroomRepository.save(chatroom));
        // 방이 생성된 이후 받은 token으로 user를 찾아 채팅입장 연관관계를 위해 저장합니다.
        Optional<User> user = userFindByToken(token);
        ChatroomMapping chatroomMapping = chatroom.addMember(user.orElse(null));
        chatroomMappingRepository.save(chatroomMapping);
        // 최종적으로 chatroomMapping 저장됬을시 방을 생성합니다.
        return createChatroom;
    }

    public Boolean joinChatroom(User user, Long newChatroomId) {
            if(chatroomMappingRepository.existsByUserIdAndChatroomId(user.getId(), newChatroomId)) {
                return false;
            }

            Chatroom chatroom = chatroomRepository.findById(newChatroomId).get();

            ChatroomMapping chatroomMapping = ChatroomMapping.builder()
                    .user(user)
                    .chatroom(chatroom)
                    .build();
            chatroomMappingRepository.save(chatroomMapping);

            return true;
    }


    public List<ChatroomDto> getChatroom(User user) {
        List<ChatroomMapping> chatroomMappingList = chatroomMappingRepository.findAllByUserId(user.getId());

        List<ChatroomDto> chatroomDtoList;
        chatroomDtoList = chatroomMappingList.stream()
                .map(chatroomMapping -> {
                    Chatroom chatroom = chatroomMapping.getChatroom();
                    return new ChatroomDto(
                            chatroom.getId(),
                            chatroom.getTitle(),
                            null,
                            chatroom.getChatroomMappingSet().size(),
                            chatroom.getCreateAt()
                    );
                }).toList();
        return chatroomDtoList;
    }

    public Optional<User> userFindByToken(String token) {
        String parseToken = token.substring("Bearer ".length()).trim();
        String username = jwtUtil.getUsername(parseToken);
        return userRepository.findByUserEmail(username);
    }
}
