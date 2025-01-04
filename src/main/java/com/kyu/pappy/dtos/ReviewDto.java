package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.User;

import java.util.Date;

public record ReviewDto(
        Long productId,
        String comment
) {

    public static ReviewDto from(Comment comment) {
        return new ReviewDto(
                comment.getCampaign().getId(),
                comment.getComment()
        );
    }

    public static Comment to(ReviewDto dto, Campaign campaign, User user) {
        return Comment.builder()
                .campaign(campaign)
                .user(user)
                .comment(dto.comment)
                .createdAt(new Date())
                .build();
    }
}
