package co.istad.lyta.ecommerce.features.order;

import co.istad.lyta.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.lyta.ecommerce.features.order.dto.OrderResponse;
import co.istad.lyta.ecommerce.features.order.dto.SetPaymentRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderService {

    OrderResponse createNew (CreateOrderRequest createOrderRequest);

    Page<OrderResponse> getAllOrderByPagination (int page, int size);

    OrderResponse getOrderById (UUID id);

    void softDeleteOrderById  (UUID id);

    void hardDeleteOrderById (UUID id);

    void setPaymentById (UUID id, SetPaymentRequest setPaymentRequest);
}
