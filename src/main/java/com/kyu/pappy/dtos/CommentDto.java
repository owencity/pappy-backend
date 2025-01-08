package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;

import java.util.Date;

public record CommentDto(
        Long storyId,
        String comment
) {

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getCommentStory().getId(),
                comment.getComment()
        );
    }

    public static Comment to(CommentDto dto, Story story, User user) {
        return Comment.builder()
                .commentStory(story)
                .user(user)
                .comment(dto.comment)
                .createdAt(new Date())
                .build();
    }
}
