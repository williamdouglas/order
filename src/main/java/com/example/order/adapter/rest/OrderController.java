package com.example.order.adapter.rest;

import com.example.order.adapter.rest.mapper.OrderRestMapper;
import com.example.order.adapter.rest.model.OrderDTO;
import com.example.order.application.domain.Order;
import com.example.order.port.in.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRestMapper orderRestMapper;

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        Order order = orderRestMapper.modelDtoToDomain(orderDTO);

        return ResponseEntity.ok(orderRestMapper.domainToModelDto(orderService.create(order)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderRestMapper.domainToModelDto(orderService.get(id)));
    }

}
