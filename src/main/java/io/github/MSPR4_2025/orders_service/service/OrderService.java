package io.github.MSPR4_2025.orders_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import io.github.MSPR4_2025.orders_service.entity.OrderEntity;
import io.github.MSPR4_2025.orders_service.mapper.OrderMapper;
import io.github.MSPR4_2025.orders_service.model.OrderCreateDto;
import io.github.MSPR4_2025.orders_service.repository.OrderRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderEntity> getOrderByUid(UUID uid) {
        return orderRepository.findByUid(uid);
    }

    public OrderEntity createOrder(OrderCreateDto orderCreate) {
        OrderEntity entity = orderMapper.fromCreateDto(orderCreate);
        

        orderRepository.save(entity);
        log.info("Order created: " + " productUid : " + entity.getProductUid() + " customerUid : " + entity.getCustomerUid());
        return entity;
    }

}
