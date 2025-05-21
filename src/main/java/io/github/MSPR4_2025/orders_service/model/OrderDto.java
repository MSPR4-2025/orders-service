package io.github.MSPR4_2025.orders_service.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private UUID uid;
    private Instant createdAt;
    private String customerUid;
    private ProductsDto products;
}
