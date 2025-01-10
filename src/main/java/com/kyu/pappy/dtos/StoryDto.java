package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

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
                        story.getComments() == null ? List.of() : story.getComments().stream()
                                .map(CommentDto::from)
                                .toList()
        );
    }

    public static Story to(StoryDto storyDto, User userId) {
        return Story.builder()
                .id(storyDto.id())
                .name(storyDto.name())
                .content(storyDto.content())
                .user(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
