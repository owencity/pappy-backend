package com.kyu.pappy.model.story;

import com.kyu.pappy.entities.Story;

import java.time.LocalDateTime;

public record StoryPageResponse(
        Long storyId,
        String name,
        LocalDateTime createAt
) {
    public static StoryPageResponse from(Story story) {
        return new StoryPageResponse(
                story.getId(),
                story.getName(),
                story.getCreatedAt()
        );
    }
}
