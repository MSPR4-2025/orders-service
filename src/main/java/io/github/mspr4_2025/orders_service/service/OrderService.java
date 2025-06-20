package io.github.mspr4_2025.orders_service.service;

import io.github.mspr4_2025.orders_service.entity.OrderEntity;
import io.github.mspr4_2025.orders_service.mapper.OrderMapper;
import io.github.mspr4_2025.orders_service.model.OrderCreateDto;
import io.github.mspr4_2025.orders_service.model.OrderUpdateDto;
import io.github.mspr4_2025.orders_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * @throws ResponseStatusException when no entity exist with the given uid.
     *                                 This exception is handled by the controllers, returning a response with the corresponding http status.
     */
    public OrderEntity getOrderByUid(UUID uid) throws ResponseStatusException {
        Optional<OrderEntity> entity = orderRepository.findByUid(uid);

        if (entity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return entity.get();
    }

    public OrderEntity createOrder(OrderCreateDto orderCreate) {
        OrderEntity entity = orderMapper.fromCreateDto(orderCreate);

        orderRepository.save(entity);
        log.info("Order created: {}, productsUid: {}, customerUid: {}", entity.getUid(), entity.getProductsUid(), entity.getCustomerUid());

        return entity;
    }


    public void deleteOrderByUid(UUID uid) {
        OrderEntity orderEntity = this.getOrderByUid(uid);

        try {
            orderRepository.delete(orderEntity);
        } catch (Exception e) {
            log.error("Error deleting order: {}", e.getMessage(), e);
        }
    }


    public void updateOrderByUid(UUID uid, OrderUpdateDto orderUpdate) {
        OrderEntity orderEntity = this.getOrderByUid(uid);

        orderMapper.updateEntityFromDto(orderUpdate, orderEntity);

        orderRepository.save(orderEntity);
    }

}
