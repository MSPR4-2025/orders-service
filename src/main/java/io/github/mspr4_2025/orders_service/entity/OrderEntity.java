package io.github.mspr4_2025.orders_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import io.github.mspr4_2025.orders_service.enums.OrderStatus;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uid = UUID.randomUUID();

    @Column(name="customer_uid")
    private UUID customerUid;

    @Column(name="products_uid")
    private List<UUID> productsUid;

    @CreatedDate
    @Column(name="created_at")
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status")
    private OrderStatus orderStatus;

    @Column(name="customer_validated")
    private boolean customerValidated;

    @Column(name="stock_validated")
    private boolean stockValidated;
}
