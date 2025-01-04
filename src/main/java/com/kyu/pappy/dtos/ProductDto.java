package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Region;
import com.kyu.pappy.entities.Campaign;
import com.kyu.pappy.enums.ProductStatus;

import java.util.Date;

public record ProductDto(
        String productName,
        String productContent,
        ProductStatus status,
        Long categoryId,
        int price,
        int quantity,
        Date productDate
) {

    public static ProductDto from(Campaign campaign) {
        return new ProductDto(
                campaign.getProductName(),
                campaign.getProductContent(),
                campaign.getStatus(),
                campaign.getRegion().getId(),
                campaign.getPrice(),
                campaign.getQuantity(),
                campaign.getCreatedAt()
        );
    }

    public static Campaign to(ProductDto dto, Region region) {
        return Campaign.builder()
                .productName(dto.productName)
                .productContent(dto.productContent)
                .status(dto.status)
                .region(region)
                .price(dto.price)
                .quantity(dto.quantity)
                .createdAt(new Date())
                .build();
    }
}
