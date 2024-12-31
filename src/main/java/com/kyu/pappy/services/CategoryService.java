package com.kyu.pappy.services;

import com.kyu.pappy.dtos.CategoryDto;
import com.kyu.pappy.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.DuplicateFormatFlagsException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(CategoryDto category) {


        boolean categoryExists = categoryRepository.existsByCategoryName(category.categoryName());

        if(categoryExists) {
//            throw new DuplicateCategoryException("이미 존재하는 카테고리 이름입니다.");
        }

        categoryRepository.save((CategoryDto.to(category)));
    }
}
