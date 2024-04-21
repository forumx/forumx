package com.example.forumx.validator;

import com.example.forumx.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean isCategoryExist(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    public boolean isCategoryNameExist(String categoryName) {
        return categoryRepository.findByName(categoryName) != null;
    }
}
