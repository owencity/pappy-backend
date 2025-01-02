package com.kyu.pappy.dtos;

import com.kyu.pappy.entities.Category;
import com.kyu.pappy.entities.Product;
import com.kyu.pappy.enums.ProductStatus;

import java.util.Date;

public record ProductDto(
        String productName,
        String productContent,
        ProductStatus status,
        Long categoryId,
        int price,
        int quantity,
        Date productDate
) {

    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getProductName(),
                product.getProductContent(),
                product.getStatus(),
                product.getCategory().getId(),
                product.getPrice(),
                product.getQuantity(),
                product.getCreatedAt()
        );
    }

    public static Product to(ProductDto dto, Category category) {
        return Product.builder()
                .productName(dto.productName)
                .productContent(dto.productContent)
                .status(dto.status)
                .category(category)
                .price(dto.price)
                .quantity(dto.quantity)
                .createdAt(new Date())
                .build();
    }
}
