package io.github.MSPR4_2025.orders_service.entity;

import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uid = UUID.randomUUID();

    private UUID customerUid;

    private UUID productUid;

    private Instant createdAt = Instant.now();

}
