package com.kyu.pappy.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CampaignPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Campaign campaign;

    private LocalDateTime participationDate;
}
