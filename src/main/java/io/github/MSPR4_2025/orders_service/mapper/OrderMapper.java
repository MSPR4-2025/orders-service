package io.github.MSPR4_2025.orders_service.mapper;


import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import io.github.MSPR4_2025.orders_service.entity.OrderEntity;
import io.github.MSPR4_2025.orders_service.model.OrderCreateDto;
import io.github.MSPR4_2025.orders_service.model.OrderDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    List<OrderDto> fromEntities(Collection<OrderEntity> entities);

    OrderDto fromEntity(OrderEntity entity);

    OrderEntity fromCreateDto(OrderCreateDto dto);
}
