package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;

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
                        .filter(c -> c.getParent() == null) // 최상위 댓글만 필터링
                        .map(CommentDto::from) // Comment → CommentDto 변환
                        .toList()

        );
    }

    public static Story to(StoryDto storyDto, User user) {
        return Story.builder()
                .id(storyDto.id())
                .name(storyDto.name())
                .content(storyDto.content())
                .imageUrl(storyDto.imageUrl())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
