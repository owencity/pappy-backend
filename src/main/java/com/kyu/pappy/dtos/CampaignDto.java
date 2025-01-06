package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.enums.CampaignStatus;

import java.util.Date;

public record CampaignDto(
        String productName,
        String productContent,
        CampaignStatus status,
        Long categoryId,
        int price,
        int quantity,
        Date productDate
) {

    public static CampaignDto from(Campaign campaign) {
        return new CampaignDto(
                campaign.getProductName(),
                campaign.getProductContent(),
                campaign.getStatus(),
                campaign.getRegion().getId(),
                campaign.getPrice(),
                campaign.getQuantity(),
                campaign.getCreatedAt()
        );
    }

    public static Campaign to(CampaignDto dto) {
        return Campaign.builder()
                .productName(dto.productName)
                .productContent(dto.productContent)
                .status(dto.status)
                .price(dto.price)
                .quantity(dto.quantity)
                .createdAt(new Date())
                .build();
    }
}
