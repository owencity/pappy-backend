package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.Story;

import java.time.LocalDateTime;
import java.util.List;

public record StoryDto(

        Long id,
        String name,
        String content,
        String imageUrl,
        LocalDateTime createdAt,
        List<CommentDto> comments
) {
    public static StoryDto from(Story story) {
        return
                new StoryDto(
                story.getId(),
                story.getName(),
                story.getContent(),
                story.getImageUrl(),
                story.getCreatedAt(),
                        story.getComments().stream()
                                .map(CommentDto::from)
                                .toList()
        );
    }

    public static Story to(StoryDto storyDto) {
        return Story.builder()
                .id(storyDto.id())
                .name(storyDto.name())
                .content(storyDto.content())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
