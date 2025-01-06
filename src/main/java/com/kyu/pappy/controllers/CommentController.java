package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.CommentDto;
import com.kyu.pappy.security.CustomUserDetails;
import com.kyu.pappy.services.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/write")
    public CommentDto SaveComment(@RequestBody CommentDto commentdto, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        return commentService.saveReview(commentdto, commentdto.campaignId() , username);
    }
}
