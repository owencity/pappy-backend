package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;

import java.util.Date;

public record CommentDto(
        Long campaignId,
        String comment
) {

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getStory().getId(),
                comment.getComment()
        );
    }

    public static Comment to(CommentDto dto, Story story, User user) {
        return Comment.builder()
                .story(story)
                .user(user)
                .comment(dto.comment)
                .createdAt(new Date())
                .build();
    }
}
