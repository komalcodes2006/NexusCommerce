package com.nexuscommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest
{
    @NotBlank
    private String name;

    private Long parentCategoryId;
}
