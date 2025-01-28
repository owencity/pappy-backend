package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.ChatroomMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomMappingRepository extends JpaRepository<ChatroomMapping, Long> {
    Boolean existsByUserIdAndChatroomId(Long userId, Long chatroomId);
    void deleteByUserIdAndChatroomId(Long userId, Long chatroomId);
    List<ChatroomMapping> findAllByUserId(Long userId);

    Optional<ChatroomMapping> findByUserIdAndChatroomId(Long userId, Long chatroomId);
}
