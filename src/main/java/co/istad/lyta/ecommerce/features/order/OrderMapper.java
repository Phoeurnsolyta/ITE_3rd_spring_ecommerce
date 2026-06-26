package co.istad.lyta.ecommerce.features.order;

import co.istad.lyta.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.lyta.ecommerce.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface OrderMapper {

    OrderResponse mapOrderToOrderResponse(Order order);

    Order mapCreateOrderRequestToOrder(CreateOrderRequest createOrderRequest);

}
