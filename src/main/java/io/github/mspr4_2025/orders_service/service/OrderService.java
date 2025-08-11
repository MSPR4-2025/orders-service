package io.github.mspr4_2025.orders_service.service;

import io.github.mspr4_2025.orders_service.entity.OrderEntity;
import io.github.mspr4_2025.orders_service.enums.OrderStatus;
import io.github.mspr4_2025.orders_service.mapper.OrderMapper;
import io.github.mspr4_2025.orders_service.model.OrderCreateDto;
import io.github.mspr4_2025.orders_service.model.OrderUpdateDto;
import io.github.mspr4_2025.orders_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    @Value("order_events_exchange")
    private String eventsExchange;

    @Value("product_service_stock_check_queue")
    private String stockCheckQueue;

    @Value("order_service_confirmation_queue")
    private String orderConfirmationQueue;

    @Value("create_order_routing")
    private String createOrderRouting;

    @Value("order_status_routing")
    private String orderConfirmationStatusRouting;


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
        entity.setOrderStatus(OrderStatus.WAITING);

        try{
            
            
            String orderJson = objectMapper.createObjectNode()
                .putPOJO("order", orderCreate)
                .put("orderUid", entity.getUid().toString())
                .toString();
            rabbitTemplate.convertAndSend(eventsExchange, createOrderRouting, orderJson);
        } catch (Exception ex){
            log.info("exception: " + ex);
        }

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

    @RabbitListener(
        bindings = @QueueBinding(
            value = @Queue(value = "order_service_confirmation_queue"),
            exchange = @Exchange(value = "order_events_exchange", type="topic"),
            key = "order_status_routing"
        )
    )
    public void handleOrderConfirmationMessage(String message) {
        try {
            log.info("message received, confirming order. Message: " + message);
            
            JsonNode orderNode = objectMapper.readTree(message);
            JsonNode orderUidNode = orderNode.get("orderUid");
            JsonNode orderStatusNode = orderNode.get("orderStatus");

            if (orderUidNode == null  || orderStatusNode == null ) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid message");
            }

            OrderEntity order = orderRepository.findByUid(UUID.fromString(orderUidNode.asText())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
            
            if (orderStatusNode.asText().equals("CONFIRMED")) {
                order.setOrderStatus(OrderStatus.CONFIRMED);
            } else {
                order.setOrderStatus(OrderStatus.CANCELED);
            }

            orderRepository.save(order);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
