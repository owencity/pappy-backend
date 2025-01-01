package com.kyu.pappy.controllers;

import com.kyu.pappy.dtos.CategoryDto;
import com.kyu.pappy.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create-category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryName) {
        categoryService.createCategory(categoryName);
        return ResponseEntity.ok().build();
    }
}
