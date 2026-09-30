package com.nexuscommerce.dto;


import com.nexuscommerce.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatusRequest
{
    private ProductStatus status;
}
