package com.example.forumx.mapper;

import com.example.forumx.entity.CategoryEntity;
import com.example.forumx.model.CategoryModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryEntity mapCategoryModelToEntity(CategoryModel categoryModel) {
        return new ModelMapper().map(categoryModel, CategoryEntity.class);
    }

    public CategoryModel mapCategoryEntityToModel(CategoryEntity categoryEntity) {
        return new ModelMapper().map(categoryEntity, CategoryModel.class);
    }
}
