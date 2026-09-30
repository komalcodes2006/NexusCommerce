package com.nexuscommerce.controller;

import com.nexuscommerce.dto.CategoryResponse;
import com.nexuscommerce.dto.CreateCategoryRequest;
import com.nexuscommerce.entity.Category;
import com.nexuscommerce.service.CategoryService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }
}