package io.github.MSPR4_2025.orders_service.controllers;

import io.github.MSPR4_2025.orders_service.entity.OrderEntity;
import io.github.MSPR4_2025.orders_service.mapper.OrderMapper;
import io.github.MSPR4_2025.orders_service.model.OrderCreateDto;
import io.github.MSPR4_2025.orders_service.model.OrderDto;
import io.github.MSPR4_2025.orders_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;


    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> listOrders() {
        List<OrderEntity> orderEntities = orderService.getAllOrders();

        return ResponseEntity.ok(orderMapper.fromEntities(orderEntities));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID uid) {
        Optional<OrderEntity> orderEntity = orderService.getOrderByUid(uid);

        return orderEntity
            .map(entity ->
                ResponseEntity.ok(orderMapper.fromEntity(entity)))
            .orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto orderCreate) {
        log.info("Creating order: " + orderCreate + " productUid : " + orderCreate.getProductUid() + " customerUid : " + orderCreate.getCustomerUid());
        if (orderCreate.getCustomerUid() == null || orderCreate.getProductUid() == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            OrderEntity orderEntity = orderService.createOrder(orderCreate);

            // Get the url to GET the created customer
            URI orderUri = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder
                    .on(getClass())
                    .getOrderById(orderEntity.getUid()))
                .build()
                .toUri();

            return ResponseEntity.created(orderUri).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID uid) {
        orderService.deleteOrderByUid(uid);
        log.info("Deleting order: " + uid);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Void> updateOrder(@PathVariable UUID uid, @RequestBody OrderCreateDto orderUpdate) {
        if (orderUpdate.getCustomerUid() == null || orderUpdate.getProductUid() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            orderService.updateOrderByUid(uid, orderUpdate);
            log.info("Updating order: " + uid + " productUid : " + orderUpdate.getProductUid() + " customerUid : " + orderUpdate.getCustomerUid());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
