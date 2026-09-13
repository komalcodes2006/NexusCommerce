package com.nexuscommerce.service;

import com.nexuscommerce.dto.CategoryResponse;
import com.nexuscommerce.dto.CreateCategoryRequest;
import com.nexuscommerce.exception.CategoryNotFoundException;
import com.nexuscommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import com.nexuscommerce.entity.Category;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }


    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        Category parent = null;

        if (request.getParentCategoryId() != null) {
            parent = repository.findById(request.getParentCategoryId())
                    .orElseThrow(() ->
                            new CategoryNotFoundException(
                                    "Parent category with id " +
                                            request.getParentCategoryId() +
                                            " not found"
                            )
                    );
        }
        category.setName(request.getName());
        category.setParentCategory(parent);

        repository.save(category);

        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
