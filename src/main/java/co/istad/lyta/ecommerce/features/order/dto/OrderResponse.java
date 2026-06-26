package co.istad.lyta.ecommerce.features.order.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID orderId,
        String customerId,
        String address,
        Float discount,
        String remark,
        Boolean status,
        LocalDateTime orderedAt,
        Boolean isDeleted
) {
}
