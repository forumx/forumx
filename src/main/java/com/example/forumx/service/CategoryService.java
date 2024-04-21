package com.example.forumx.service;

import com.example.forumx.entity.CategoryEntity;
import com.example.forumx.exception.DuplicateException;
import com.example.forumx.exception.NotFoundException;
import com.example.forumx.mapper.CategoryMapper;
import com.example.forumx.model.CategoryModel;
import com.example.forumx.repository.CategoryRepository;
import com.example.forumx.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final CategoryValidator categoryValidator;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categoryValidator = categoryValidator;
    }

    public void createCategory(CategoryModel categoryModel) {
        if (categoryValidator.isCategoryNameExist(categoryModel.getName())) {
            throw new DuplicateException("Category name already exists");
        }
        CategoryEntity categoryEntity = categoryMapper.mapCategoryModelToEntity(categoryModel);
        categoryEntity.setThreadCount(0);
        categoryRepository.save(categoryEntity);
    }

    public CategoryModel updateCategory(CategoryModel categoryModel, Long categoryId) {
        if (!categoryValidator.isCategoryExist(categoryId)) {
            throw new NotFoundException("Category does not exist");
        }
        CategoryEntity categoryEntity = categoryMapper.mapCategoryModelToEntity(categoryModel);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.mapCategoryEntityToModel(categoryEntity);
    }

//    public void deleteCategory(Long categoryId) {
//        if (!categoryValidator.isCategoryExist(categoryId)) {
//            throw new NotFoundException("Category does not exist");
//        }
//
//
//        categoryRepository.deleteById(categoryId);
//    }

    public CategoryModel getCategory(Long categoryId) {
        if (!categoryValidator.isCategoryExist(categoryId)) {
            throw new NotFoundException("Category does not exist");
        }
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
        return categoryMapper.mapCategoryEntityToModel(categoryEntity);
    }

    public Page<CategoryModel> getAllCategories(int currentPage, int pageSize) {
        Sort sortBy = Sort.by("name").ascending();
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sortBy);
        Page<CategoryEntity> categoryEntities = categoryRepository.findAll(pageRequest);
        return categoryEntities.map(categoryMapper::mapCategoryEntityToModel);
    }

    public List<CategoryModel> getCategoriesById(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds).stream().map(categoryMapper::mapCategoryEntityToModel).toList();
    }

    public CategoryModel fetchCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
        return categoryMapper.mapCategoryEntityToModel(categoryEntity);
    }

    public void updateThreadCount(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
        assert categoryEntity != null;
        categoryEntity.setThreadCount(categoryEntity.getThreadCount() + 1);
        categoryRepository.save(categoryEntity);
    }
}
