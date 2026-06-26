package co.istad.lyta.ecommerce.features.order;

import co.istad.lyta.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.lyta.ecommerce.features.order.dto.OrderResponse;
import co.istad.lyta.ecommerce.features.order.dto.SetPaymentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createNew (
          @Valid @RequestBody CreateOrderRequest createOrderRequest
    ) {
        return orderService.createNew(createOrderRequest);
    }

    @ResponseStatus (HttpStatus.OK)
    @GetMapping
    public Page<OrderResponse> getAllOrderByPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size) {

        return orderService.getAllOrderByPagination(page, size);
    }

    @ResponseStatus (HttpStatus.OK)
    @GetMapping("/{id}")
    public OrderResponse getOrderById(
            @PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    @ResponseStatus (HttpStatus.NO_CONTENT)
    @PutMapping ("/{id}/soft-delete")
    public void softDeleteOrderById(@PathVariable UUID id) {
        orderService.softDeleteOrderById(id);
    }

    @ResponseStatus (HttpStatus.NO_CONTENT)
    @DeleteMapping ("/{id}")
    public void hardDeleteOrderById(@PathVariable UUID id) {
        orderService.hardDeleteOrderById(id);
    }

    @ResponseStatus (HttpStatus.NO_CONTENT)
    @PutMapping ("/{id}/status")
    public void softDeleteOrderById(@PathVariable UUID id, SetPaymentRequest setPaymentRequest) {
        orderService.setPaymentById(id,setPaymentRequest);
    }


}
