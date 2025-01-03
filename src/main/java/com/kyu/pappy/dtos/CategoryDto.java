package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Category;

import java.util.Date;

public record CategoryDto(
        Long id,
        String categoryName,
        Date createdDate
) {
    public static CategoryDto from(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getCategoryName(),
                null
        );
    }

    public static Category to(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.id())
                .categoryName(categoryDto.categoryName())
                .createdAt(new Date())
                .build();
    }
}
