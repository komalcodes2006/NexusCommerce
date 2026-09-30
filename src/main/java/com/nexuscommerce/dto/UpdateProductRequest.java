package com.nexuscommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private int stockQuantity;

    private String description;
}