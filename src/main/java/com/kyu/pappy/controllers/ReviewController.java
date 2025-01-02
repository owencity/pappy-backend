package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.ReviewDto;
import com.kyu.pappy.security.CustomUserDetails;
import com.kyu.pappy.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/write")
    public ReviewDto SaveReview(@RequestBody ReviewDto dto, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        return reviewService.saveReview(dto, dto.productId() , username);
    }
}
