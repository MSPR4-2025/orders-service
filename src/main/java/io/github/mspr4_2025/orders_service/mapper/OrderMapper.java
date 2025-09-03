package io.github.mspr4_2025.orders_service.mapper;


import io.github.mspr4_2025.orders_service.entity.OrderEntity;
import io.github.mspr4_2025.orders_service.model.OrderCreateDto;
import io.github.mspr4_2025.orders_service.model.OrderDto;
import io.github.mspr4_2025.orders_service.model.OrderUpdateDto;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

@Mapper
public abstract class OrderMapper {
    // Read value from properties file
    @Value("${app.zoneId:UTC}")
    private String zoneId;

    public abstract List<OrderDto> fromEntities(Collection<OrderEntity> entities);

    public abstract OrderDto fromEntity(OrderEntity entity);

    public abstract OrderEntity fromCreateDto(OrderCreateDto dto);

    public abstract void updateEntityFromDto(OrderUpdateDto dto, @MappingTarget OrderEntity entity);

    /**
     * Convert an {@link Instant} into the corresponding {@link OffsetDateTime}
     * at the zoneId specified in the properties file
     */
    @Nullable
    protected OffsetDateTime fromInstant(@Nullable Instant instant) {
        return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneId.of(zoneId));
    }
}
