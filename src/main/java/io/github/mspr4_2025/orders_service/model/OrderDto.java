package io.github.mspr4_2025.orders_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private UUID uid;
    private OffsetDateTime createdAt;
    private String customerUid;
    private ProductsDto products;
}
