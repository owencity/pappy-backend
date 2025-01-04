package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Region;

import java.util.Date;

public record CategoryDto(
        Long id,
        String categoryName,
        Date createdDate
) {
    public static CategoryDto from(Region region) {
        return new CategoryDto(
                region.getId(),
                region.getCategoryName(),
                null
        );
    }

    public static Region to(CategoryDto categoryDto) {
        return Region.builder()
                .id(categoryDto.id())
                .categoryName(categoryDto.categoryName())
                .createdAt(new Date())
                .build();
    }
}
