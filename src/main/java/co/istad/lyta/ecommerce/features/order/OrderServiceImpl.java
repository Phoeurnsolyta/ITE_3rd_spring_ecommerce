package co.istad.lyta.ecommerce.features.order;

import co.istad.lyta.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.lyta.ecommerce.features.order.dto.OrderResponse;
import co.istad.lyta.ecommerce.features.order.dto.SetPaymentRequest;
import co.istad.lyta.ecommerce.features.product.Product;
import co.istad.lyta.ecommerce.features.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {

        final Order order = orderMapper.mapCreateOrderRequestToOrder(createOrderRequest);

//        validate order line (List)
        List<OrderLine> orderLines = new ArrayList<>();
        boolean isValidOder = createOrderRequest.orderLines().stream()
                .allMatch(orderLineDto -> {
                    Optional<Product> productOptional = productRepository.findByCode(orderLineDto.code());

                    if (productOptional.isPresent()) {
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(productOptional.get());
                        orderLine.setQty(orderLineDto.qty());
                        orderLine.setUnitPrice(orderLineDto.unitPrice());
                        orderLines.add(orderLine);
                        return true;
                    }
                    return false;
                });
        if (!isValidOder) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order line");
        }

        order.setOrderLines(orderLines);
//        security related
        order.setCustomerId("Lyta");

        order.setIsDeleted(false);
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(false);

        Order savedOrder = orderRepository.save(order);
//        orderRepository.save(order);
        return orderMapper.mapOrderToOrderResponse(savedOrder);
    }

    @Override
    public Page<OrderResponse> getAllOrderByPagination(int page, int size) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size);

        return orderRepository.findAll(pageable)
                .map(orderMapper::mapOrderToOrderResponse);
    }

    @Override
    public OrderResponse getOrderById(UUID id) {
        Order getById = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Order has not been found"));

        return orderMapper.mapOrderToOrderResponse(getById);
    }

    @Override
    public void softDeleteOrderById(UUID id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order has not been found"));

        order.setIsDeleted(true);
        orderRepository.save(order);
    }

    @Override
    public void hardDeleteOrderById(UUID id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Order has not been found"));
        orderRepository.deleteById(id);
    }

    @Override
    public void setPaymentById(UUID id, SetPaymentRequest setPaymentRequest) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order has not been found"));

        order.setStatus(setPaymentRequest.status());
        order.setStatus(true);

        orderRepository.save(order);
    }
}