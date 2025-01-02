package com.kyu.pappy.services;

import com.kyu.pappy.config.exceptions.user.UserNotFoundException;
import com.kyu.pappy.dtos.ReviewDto;
import com.kyu.pappy.entities.Product;
import com.kyu.pappy.entities.Review;
import com.kyu.pappy.entities.User;
import com.kyu.pappy.repositories.ProductRepository;
import com.kyu.pappy.repositories.ReviewRepository;
import com.kyu.pappy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ReviewDto saveReview(ReviewDto reviewDto, Long productId, String username ) {

        User user = userRepository.findByUserEmail(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
        Product product = productRepository.findById(productId).orElseThrow( () -> new RuntimeException("not found product"));

        Review createReview = reviewRepository.save(ReviewDto.to(reviewDto, product, user));
        return ReviewDto.from(createReview);
    }
}
