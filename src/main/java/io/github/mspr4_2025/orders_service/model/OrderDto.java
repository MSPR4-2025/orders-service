package io.github.mspr4_2025.orders_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderDto {
    private UUID uid;
    private UUID customerUid;
    private List<UUID> productsUid;
    private OffsetDateTime createdAt;
}
