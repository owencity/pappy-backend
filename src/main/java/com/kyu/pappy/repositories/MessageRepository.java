package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllById(Long chatroomId);
}
