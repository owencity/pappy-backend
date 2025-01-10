package com.kyu.pappy.entities;

import com.kyu.pappy.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campaignName;
    private String campaignContent;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;
    private LocalDateTime createdAt;

    public void changeContent(String changeContent) {
        this.campaignContent = changeContent;
    }

}
