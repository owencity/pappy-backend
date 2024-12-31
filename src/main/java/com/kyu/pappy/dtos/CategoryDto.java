package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Category;

public record CategoryDto(
        String categoryName
) {
    public static CategoryDto from(Category category) {
        return new CategoryDto(
              category.getCategoryName()
        );
    }

    public static Category to(CategoryDto categoryDto) {
        return Category.builder()
                .categoryName(categoryDto.categoryName())
                .build();
    }
}
