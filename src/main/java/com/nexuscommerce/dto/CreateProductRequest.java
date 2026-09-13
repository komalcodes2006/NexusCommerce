package com.nexuscommerce.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest
{
    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private int stockQuantity;

    private String description;

    @NotNull
    private Long categoryId;
}
