package com.example.order.adapter.rest;

import com.example.order.adapter.rest.mapper.OrderRestMapper;
import com.example.order.adapter.rest.model.OrderDTO;
import com.example.order.application.domain.Order;
import com.example.order.port.in.OrderInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller responsible for handling HTTP requests related to orders.
 * This controller acts as an adapter in the hexagonal architecture,
 * converting HTTP requests to domain operations.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRestMapper orderRestMapper;

    private final OrderInputPort orderInputPort;

    /**
     * Creates a new order.
     *
     * @param orderDTO the order data transfer object containing order information
     * @return ResponseEntity containing the created order as OrderDTO
     * @throws RestExceptionHandler if there's an error during order creation
     */
    @PostMapping()
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        Order order = orderRestMapper.modelDtoToDomain(orderDTO);

        return ResponseEntity.ok(orderRestMapper.domainToModelDto(orderInputPort.create(order)));
    }

    /**
     * Retrieves a specific order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return ResponseEntity containing the order as OrderDTO
     * @throws ProductNotFoundException if the order with the given ID is not found
     * @throws RestExceptionHandler if there's an error during order retrieval
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderRestMapper.domainToModelDto(orderInputPort.get(id)));
    }

    /**
     * Retrieves all existing orders in the system.
     *
     * @return ResponseEntity containing a list of all orders as OrderDTO
     * @throws RestExceptionHandler if there's an error during orders retrieval
     */
    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getAll() {
        List<Order> orders = orderInputPort.getAll();
        List<OrderDTO> orderDTOs = orders.stream()
                .map(orderRestMapper::domainToModelDto)
                .toList();
        return ResponseEntity.ok(orderDTOs);
    }

}
