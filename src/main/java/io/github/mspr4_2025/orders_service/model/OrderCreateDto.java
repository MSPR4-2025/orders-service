package io.github.mspr4_2025.orders_service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderCreateDto {

    private UUID customerUid;
    private List<UUID> productsUid;


}
