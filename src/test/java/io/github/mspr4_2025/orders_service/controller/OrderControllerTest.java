package io.github.mspr4_2025.orders_service.controller;

import io.github.mspr4_2025.orders_service.model.OrderCreateDto;
import io.github.mspr4_2025.orders_service.model.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {
    @Autowired
    WebTestClient client;

    @Sql("classpath:/sql/OrderController/orders.sql")
    @Test
    void testGetOrderList() {
        EntityExchangeResult<List<OrderDto>> result = client.get()
            .uri("/api/orders")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(OrderDto.class)
            .returnResult();

        assertThat(result.getResponseBody()).isNotEmpty();
        assertThat(result.getResponseBody()).hasSize(2);
        assertThat(result.getResponseBody()).anyMatch(orderDto ->
            orderDto.getCustomerUid().equals(UUID.fromString("939c7754-d2b8-43c9-b710-6740f3a0ee61")));
    }

    @Test
    void testCreateOrder() {
        OrderCreateDto orderCreate = new OrderCreateDto();
        orderCreate.setCustomerUid(UUID.fromString("939c7754-d2b8-43c9-b710-6740f3a0ee61"));
        orderCreate.setProductsUid(List.of(
            UUID.fromString("939c7754-d2b9-43c8-b820-6740f3a0ee61"),
            UUID.fromString("939c7754-d2b9-43c8-b820-6740f3a0ee61")
        ));

        EntityExchangeResult<Void> result = client.post()
            .uri("/api/orders")
            .bodyValue(orderCreate)
            .exchange()
            .expectStatus().isCreated()
            .expectBody().isEmpty();

        URI orderLocation = result.getResponseHeaders().getLocation();
        assertThat(orderLocation).isNotNull();

        EntityExchangeResult<OrderDto> orderResult = client.get()
            .uri(orderLocation)
            .exchange()
            .expectStatus().isOk()
            .expectBody(OrderDto.class)
            .returnResult();

        assertThat(orderResult.getResponseBody()).isNotNull();
        assertThat(orderResult.getResponseBody().getCustomerUid()).isEqualTo(orderCreate.getCustomerUid());
    }
}
