package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;

import java.util.Date;
import java.util.List;

public record CommentDto(
        Long id,
        Long storyId,
        Long parentId,
        String comment,
        List<CommentDto> replies // 대댓글 리스트

) {

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getCommentStory().getId(),
                comment.getParent() != null ? comment.getParent().getId() : null,
                comment.getComment(),
                comment.getReplies() != null ? comment.getReplies().stream().map(CommentDto::from).toList()
                        : null
        );
    }

    public static Comment to(CommentDto dto, Story story, User user, Comment parent) {
        return Comment.builder()
                .commentStory(story)
                .user(user)
                .parent(parent) // 부모 댓글 설정
                .comment(dto.comment)
                .createdAt(new Date())
                .build();
    }
}
