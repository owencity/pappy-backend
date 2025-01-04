package com.kyu.pappy.entities;

import jakarta.persistence.*;

@Entity
public class VolunteerStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String content;
    private String imageUrl;
    private String condition;

    @ManyToOne
    private User user;

    @ManyToOne
    private Campaign campaign;
}
