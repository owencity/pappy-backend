package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.CommentDto;
import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.Comment;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.repositories.CampaignRepository;
import com.kyu.pappy.repositories.CommentRepository;
import com.kyu.pappy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, CampaignRepository campaignRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
    }

    public CommentDto saveReview(CommentDto commentDto, Long campaignId, String username ) {

        User user = userRepository.findByUserEmail(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow( () -> new RuntimeException("not found product"));

        Comment createComment = commentRepository.save(CommentDto.to(commentDto, campaign, user));
        return CommentDto.from(createComment);
    }
}
