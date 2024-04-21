package com.example.forumx.api;

import com.example.forumx.model.CategoryModel;
import com.example.forumx.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categories")
    public Page<CategoryModel> getCategories(@RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size){
        return categoryService.getAllCategories(page, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/categories")
    public void createCategory(@RequestBody CategoryModel categoryModel) {
         categoryService.createCategory(categoryModel);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryModel> getCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryModel> updateCategory(@RequestBody CategoryModel categoryModel, @PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryModel, categoryId));
    }
}

