package io.github.mspr4_2025.orders_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Orders Service")
                .version("0.1"));
    }
}
