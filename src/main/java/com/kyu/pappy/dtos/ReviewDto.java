package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Product;
import com.kyu.pappy.entities.Review;
import com.kyu.pappy.entities.User;

import java.util.Date;

public record ReviewDto(
        Long productId,
        String comment
) {

    public static ReviewDto from(Review review) {
        return new ReviewDto(
                review.getProduct().getId(),
                review.getComment()
        );
    }

    public static Review to(ReviewDto dto, Product product, User user) {
        return Review.builder()
                .product(product)
                .user(user)
                .comment(dto.comment)
                .createdAt(new Date())
                .build();
    }
}
