package io.github.MSPR4_2025.orders_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:database.properties")
public class OrdersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

}
