package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.ChatMessageDto;
import com.kyu.pappy.dtos.ChatroomDto;
import com.kyu.pappy.entities.Chatroom;
import com.kyu.pappy.entities.ChatroomMapping;
import com.kyu.pappy.entities.Message;
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
        User user = userFindByToken(token)
                .orElseThrow(UserNotFoundException::new);
        ChatroomMapping chatroomMapping = chatroom.addMember(user);
        chatroomMappingRepository.save(chatroomMapping);
        // 최종적으로 chatroomMapping 저장됬을시 방을 생성합니다.
        return createChatroom;
    }

    public Boolean joinChatroom(String token, Long newChatroomId, Long currentChatroomId) {

        User user = userFindByToken(token)
                .orElseThrow(UserNotFoundException::new);

        if(currentChatroomId != null) {
            updateLastCheckeAt(user, currentChatroomId)
        }
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

    public Boolean leaveChatroom(String token, Long chatroomId) {
        User user = userFindByToken(token)
                .orElseThrow(UserNotFoundException::new);
        if(!chatroomMappingRepository.existsByUserIdAndChatroomId(user.getId(), chatroomId)) {
            return false;
        }

        chatroomMappingRepository.deleteByUserIdAndChatroomId(user.getId(), chatroomId);
        return true;
    }

    private void updateLastCheckeAt(User user, Long currentChatroomId) {
        if (currentChatroomId == null) {
            return ;
        }
        ChatroomMapping chatroomMapping = chatroomMappingRepository.findByUserIdAndChatroomId(user.getId(),currentChatroomId)
                .orElseThrow(UserNotFoundException::new);
        // exception 처리 변경 필요
        chatroomMapping.updateLastCheckedAt();

        chatroomMappingRepository.save(chatroomMapping);


    }




    public List<ChatroomDto> getChatroomList(User user) {
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

    public void saveMessage(String username, Long chatroomId, String text) {
        Chatroom chatroom = chatroomRepository.findById(chatroomId).get();

        Optional<User> saveUser = userRepository.findByUserEmail(username);

        Message message = Message.builder()
                .text(text)
                .nickname(username)
                .chatroomId(chatroom.getId())
                .createAt(LocalDateTime.now())
                .build();

    }

    public Optional<User> userFindByToken(String token) {
        String parseToken = token.substring("Bearer ".length()).trim();
        String username = jwtUtil.getUsername(parseToken);
        return userRepository.findByUserEmail(username);
    }
}
