package io.github.MSPR4_2025.orders_service.mapper;


import io.github.MSPR4_2025.orders_service.entity.OrderEntity;
import io.github.MSPR4_2025.orders_service.model.OrderCreateDto;
import io.github.MSPR4_2025.orders_service.model.OrderDto;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    List<OrderDto> fromEntities(Collection<OrderEntity> entities);

    OrderDto fromEntity(OrderEntity entity);

    OrderEntity fromCreateDto(OrderCreateDto dto);
}
