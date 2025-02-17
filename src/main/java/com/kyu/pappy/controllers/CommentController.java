package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.CommentDto;
import com.kyu.pappy.model.comment.CommentRequestBody;
import com.kyu.pappy.security.CustomUserDetails;
import com.kyu.pappy.services.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/write")
    public CommentDto saveComment(@RequestBody CommentDto commentdto, Authentication auth) {


        return commentService.saveComment(commentdto, commentdto.storyId() ,auth, commentdto.parentId());
    }

    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId, Authentication auth) {
        commentService.deleteComment(commentId, auth);
    }

    @PatchMapping("/update/{commentId}")
    public void updateComment(@PathVariable Long commentId, Authentication auth, CommentRequestBody commentRequestBody) {
        commentService.updateComment(commentId, auth, commentRequestBody);
    }

    // 대댓글 불러오기
    @GetMapping("/comments/{parentId}/replies")
    public List<CommentDto> getRepliesByCommentId(@PathVariable Long parentId) {
        return commentService.findRepliesByCommentId(parentId);
    }

}
