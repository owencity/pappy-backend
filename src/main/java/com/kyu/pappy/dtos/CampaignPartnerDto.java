package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.entities.CampaignPartner;
import com.kyu.pappy.entities.User;

import java.time.LocalDateTime;

public record CampaignPartnerDto(
        Long userId,
        Long campaignId,
        LocalDateTime createAt
) {
    public static CampaignPartnerDto from(CampaignPartner campaignPartner) {
        return new CampaignPartnerDto(
                campaignPartner.getUser().getId(),
                campaignPartner.getCampaign().getId(),
                campaignPartner.getParticipationDate()
        );
    }

    public static CampaignPartner to(User userId, Campaign campaignId) {
        return CampaignPartner.builder()
                .user(userId)
                .campaign(campaignId)
                .participationDate(LocalDateTime.now())
                .build();
    }
}
