package com.example.bookmanager.mapper;

import com.example.bookmanager.dto.category.CategoryResponse;
import com.example.bookmanager.dto.category.CreateCategoryRequest;
import com.example.bookmanager.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryRequest createCategoryRequest){
        Category category = new Category();

        category.setName(createCategoryRequest.getName());

        return category;
    }

    public CategoryResponse toDto(Category category){
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());

        return categoryResponse;
    }
}
