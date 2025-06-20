package io.github.mspr4_2025.orders_service.controllers;

import io.github.mspr4_2025.orders_service.entity.OrderEntity;
import io.github.mspr4_2025.orders_service.mapper.OrderMapper;
import io.github.mspr4_2025.orders_service.model.OrderCreateDto;
import io.github.mspr4_2025.orders_service.model.OrderDto;
import io.github.mspr4_2025.orders_service.model.OrderUpdateDto;
import io.github.mspr4_2025.orders_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;


    @GetMapping
    public ResponseEntity<List<OrderDto>> listOrders() {
        List<OrderEntity> orderEntities = orderService.getAllOrders();

        return ResponseEntity.ok(orderMapper.fromEntities(orderEntities));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID uid) {
        OrderEntity orderEntity = orderService.getOrderByUid(uid);

        return ResponseEntity.ok(orderMapper.fromEntity(orderEntity));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto orderCreate) {
        log.info("Creating order: {}, productsUid: {}, customerUid: {}", orderCreate, orderCreate.getProductsUid(), orderCreate.getCustomerUid());

        if (orderCreate.getCustomerUid() == null || CollectionUtils.isEmpty(orderCreate.getProductsUid())) {
            return ResponseEntity.badRequest().build();
        }

        OrderEntity orderEntity = orderService.createOrder(orderCreate);

        // Get the url to GET the created customer
        URI orderUri = MvcUriComponentsBuilder
            .fromMethodCall(MvcUriComponentsBuilder
                .on(getClass())
                .getOrderById(orderEntity.getUid()))
            .build()
            .toUri();

        return ResponseEntity.created(orderUri).build();
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID uid) {
        log.info("Deleting order: {}", uid);

        orderService.deleteOrderByUid(uid);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Void> updateOrder(@PathVariable UUID uid, @RequestBody OrderUpdateDto orderUpdate) {
        log.info("Updating order: {}, productsUid: {}, customerUid: {}", uid, orderUpdate.getProductsUid(), orderUpdate.getCustomerUid());

        if (orderUpdate.getCustomerUid() == null || CollectionUtils.isEmpty(orderUpdate.getProductsUid())) {
            return ResponseEntity.badRequest().build();
        }

        orderService.updateOrderByUid(uid, orderUpdate);

        return ResponseEntity.ok().build();
    }
}
