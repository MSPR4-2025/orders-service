package io.github.mspr4_2025.orders_service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderCreateDto {

    private UUID customerUid;
    private UUID productUid;


}
