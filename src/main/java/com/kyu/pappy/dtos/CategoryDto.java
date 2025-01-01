package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Category;

import java.util.Date;

public record CategoryDto(
        String categoryName,
        Date createdDate
) {
    public static CategoryDto from(Category category) {
        return new CategoryDto(
              category.getCategoryName(),
                null
        );
    }

    public static Category to(CategoryDto categoryDto) {
        return Category.builder()
                .categoryName(categoryDto.categoryName())
                .createdAt(new Date())
                .build();
    }
}
