package io.github.mspr4_2025.orders_service.service;

import io.github.mspr4_2025.orders_service.entity.OrderEntity;
import io.github.mspr4_2025.orders_service.mapper.OrderMapper;
import io.github.mspr4_2025.orders_service.model.OrderCreateDto;
import io.github.mspr4_2025.orders_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


    public void deleteOrderByUid(UUID uid) {
        try {
            OrderEntity orderEntity = orderRepository.findByUid(uid).orElseThrow(() -> new RuntimeException("Order not found"));
            orderRepository.delete(orderEntity);
        } catch (Exception e) {
            log.error("Error deleting order: " + e.getMessage());
        }
    }


    public void updateOrderByUid(UUID uid, OrderCreateDto orderUpdate) {
        OrderEntity orderEntity = orderRepository.findByUid(uid).orElseThrow(() -> new RuntimeException("Order not found"));
        orderEntity.setCustomerUid(orderUpdate.getCustomerUid());
        orderEntity.setProductUid(orderUpdate.getProductUid());
        orderRepository.save(orderEntity);
    }

}
