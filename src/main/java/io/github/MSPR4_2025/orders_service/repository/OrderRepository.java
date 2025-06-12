package io.github.MSPR4_2025.orders_service.repository;

import io.github.MSPR4_2025.orders_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByUid(UUID uid);

    List<OrderEntity> findByCustomerUid(UUID customerUuid);

    List<OrderEntity> findByProductUid(UUID productUuid);

}
