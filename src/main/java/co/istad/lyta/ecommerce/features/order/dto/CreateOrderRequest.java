package co.istad.lyta.ecommerce.features.order.dto;

import co.istad.lyta.ecommerce.features.order.OrderLine;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreateOrderRequest(
        @NotBlank(message = "Address is required")
        String address,

        @NotNull(message = "Discount is required")
        @Min(0)
        @Max(100)
        Float discount,

        Boolean status,
        @Size(max = 250)
        String remark,

        @NotEmpty (message = "Order line is required")
        List<OrderLineDto> orderLines
) {
}
