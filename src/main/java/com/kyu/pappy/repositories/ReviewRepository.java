package com.kyu.pappy.repositories;

import com.kyu.pappy.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
