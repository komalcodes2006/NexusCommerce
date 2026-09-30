package com.nexuscommerce.controller;
import com.nexuscommerce.dto.*;
import com.nexuscommerce.service.ProductService;
import jakarta.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController
{
    private final ProductService productService;

    public ProductController(ProductService productService) {

        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest request)
    {
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAll()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @Valid @RequestBody UpdateProductRequest request)
    {

        return productService.updateProduct(id, request);
    }

    @PatchMapping("/{id}/status")
    public ProductResponse updateProductStatus(
            @PathVariable Long id,
            @RequestBody ProductStatusRequest request) {

        return productService.updateProductStatus(id, request);
    }

    @PatchMapping("/{id}/category")
    public ProductResponse updateProductCategory(
            @PathVariable Long id,
            @RequestBody ProductCategoryRequest request) {

        return productService.updateProductCategory(id, request);
    }

}
