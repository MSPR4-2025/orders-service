package io.github.MSPR4_2025.orders_service.model;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
public class OrderCreateDto {
    
    private UUID customerUid;
    private UUID productUid;
    

}
