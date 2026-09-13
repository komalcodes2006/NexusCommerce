package com.nexuscommerce.dto;
import com.nexuscommerce.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse
{
        private Long id;
        private String name;
        private BigDecimal price;
        private int stockQuantity;
        private String description;
        private ProductStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private CategoryResponse category;

}
