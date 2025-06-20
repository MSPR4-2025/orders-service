package io.github.mspr4_2025.orders_service.repository;

import io.github.mspr4_2025.orders_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByUid(UUID uid);
}
