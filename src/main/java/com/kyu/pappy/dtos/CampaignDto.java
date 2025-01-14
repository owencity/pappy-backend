package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.enums.CampaignStatus;

import java.time.LocalDateTime;

public record CampaignDto(
        String campaignName,
        String campaignContent,
        CampaignStatus status,
        Long regionId,
        LocalDateTime createAt
) {

    public static CampaignDto from(Campaign campaign) {
        return new CampaignDto(
                campaign.getCampaignName(),
                campaign.getCampaignContent(),
                campaign.getStatus(),
                null,
                campaign.getCreatedAt()
        );
    }

    public static Campaign to(CampaignDto dto) {
        return Campaign.builder()
                .campaignName(dto.campaignName)
                .campaignContent(dto.campaignContent)
                .status(dto.status)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
