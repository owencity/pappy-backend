package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.User;

import java.util.Date;

public record CommentDto(
        Long campaignId,
        String comment
) {

    public static CommentDto from(Comment comment) {
        return new CommentDto(
                comment.getCampaign().getId(),
                comment.getComment()
        );
    }

    public static Comment to(CommentDto dto, Campaign campaign, User user) {
        return Comment.builder()
                .campaign(campaign)
                .user(user)
                .comment(dto.comment)
                .createdAt(new Date())
                .build();
    }
}
