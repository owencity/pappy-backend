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

    public CommentDto saveComment(CommentDto commentDto, Long campaignId, String username ) {

        User user = userRepository.findByUserEmail(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );

        Story story = storyRepository.findById(campaignId).orElseThrow( () -> new RuntimeException("not found storyId"));

        Comment createComment = commentRepository.save(CommentDto.to(commentDto, story, user));
        return CommentDto.from(createComment);
    }
}
