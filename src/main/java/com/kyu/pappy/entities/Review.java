package com.kyu.pappy.entities;

import com.fasterxml.jackson.annotation.JacksonInject;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 사용자가 여러개의 리뷰
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 하나의 상품에 여러개의 리뷰
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private String comment;
    private Date createdAt;
}
