package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.CategoryDto;
import com.kyu.pappy.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ResponseEntity<?> createCategory(CategoryDto categoryName) {
        categoryService.createCategory(categoryName);
        return ResponseEntity.ok().build();
    }
}
