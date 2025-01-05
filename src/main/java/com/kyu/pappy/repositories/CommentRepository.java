package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
