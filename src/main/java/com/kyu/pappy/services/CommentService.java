package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.CommentDto;
import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.Story;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.repositories.CommentRepository;
import com.kyu.pappy.repositories.StoryRepository;
import com.kyu.pappy.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,StoryRepository storyRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }

    public CommentDto saveComment(CommentDto commentDto, Long campaignId, String username, Long parentId ) {

        // 1. 사용자 확인
        User user = userRepository.findByUserEmail(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );

        // 2. 스토리 확인
        Story story = storyRepository.findById(campaignId).orElseThrow( () -> new RuntimeException("not found storyId"));

        // 3. 부모 댓글 확인 (대댓글 일 경우만)
        Comment parent = (parentId != null)
                ? commentRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent comment not found" + parentId))
                : null;

            // 자바 기본문법 -> 지역변수 초기화 필수, 참조타입변수 -> null로 초기화(아직 참조할 객체가없다를 명시적으로 표현)
            // 대댓글시 부모를 가리키는 id null 일시 새댓글, 부모를 가리키는 parentId 있을시 대댓글
        Comment createComment = commentRepository.save(CommentDto.to(commentDto, story, user, parent));
        return CommentDto.from(createComment);
    }

}
