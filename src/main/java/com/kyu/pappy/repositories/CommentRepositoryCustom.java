package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> getCommentsWithReplies(Long storyId);
    
}
