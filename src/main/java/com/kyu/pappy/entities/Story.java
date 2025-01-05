package com.kyu.pappy.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    public void changeContent(String changeContent) {
        this.content = changeContent;
    }
}
