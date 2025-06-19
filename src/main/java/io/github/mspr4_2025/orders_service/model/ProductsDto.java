package io.github.mspr4_2025.orders_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsDto {
    private String id;
    private String name;
    private String createdAt;
    private ProductDetailsDto details;
    private int stock;
    private String orderId;
}
