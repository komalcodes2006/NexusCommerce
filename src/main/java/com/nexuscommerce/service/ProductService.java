package com.nexuscommerce.service;

import com.nexuscommerce.dto.*;
import com.nexuscommerce.entity.Category;
import com.nexuscommerce.entity.Product;
import com.nexuscommerce.entity.ProductStatus;
import com.nexuscommerce.exception.CategoryNotFoundException;
import com.nexuscommerce.exception.ProductNotFoundException;
import com.nexuscommerce.repository.CategoryRepository;
import com.nexuscommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productrepository, CategoryRepository categoryrepository) {

        this.productRepository = productrepository;
        this.categoryRepository= categoryrepository;
    }

    public ProductResponse createProduct(CreateProductRequest request)
    {
        System.out.println("Service method called!");
        Product product = new Product();

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category with id " +
                                        request.getCategoryId() +
                                        " not found"
                        )
                );

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setDescription(request.getDescription());
        product.setStatus(ProductStatus.ACTIVE);
        product.setCategory(category);
        productRepository.save(product);
        System.out.println("Product saved!");
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getDescription(),
            product.getStatus(),
            product.getCreatedAt(),
            product.getUpdatedAt(),
            new CategoryResponse(
                    product.getCategory().getId(),
                    product.getCategory().getName()
            ));

    }
    public List<ProductResponse> getAllProducts()
    {
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : productRepository.findAll()) {
            responses.add(new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStockQuantity(),
                    product.getDescription(),
                    product.getStatus(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    new CategoryResponse(
                            product.getCategory().getId(),
                            product.getCategory().getName()
                    )
            ));
        }

        return responses;
    }

    public ProductResponse getProductById(Long id)
    {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                new CategoryResponse(
                product.getCategory().getId(),
                product.getCategory().getName()
        )
        );
    }

    public ProductResponse updateProduct(
            Long id,
            UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setDescription(request.getDescription());

        productRepository.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
        new CategoryResponse(
                product.getCategory().getId(),
                product.getCategory().getName()
        )
        );
    }

    public ProductResponse updateProductStatus(Long id, ProductStatusRequest request)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );
        product.setStatus(request.getStatus());

        productRepository.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                new CategoryResponse(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
        );
    }

    public ProductResponse updateProductCategory(Long id, ProductCategoryRequest request)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with id " + id + " not found"
                        )
                );

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category with id " +
                                        request.getCategoryId() +
                                        " not found"
                        )
                );
        product.setCategory(category);
        productRepository.save(product);
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                new CategoryResponse(
                        product.getCategory().getId(),
                        product.getCategory().getName()
                )
        );
    }
}
