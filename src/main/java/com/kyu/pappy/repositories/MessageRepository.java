package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllById(Long chatroomId);

    Optional<Message> findAllByChatroomId(Long chatroomId);
}
