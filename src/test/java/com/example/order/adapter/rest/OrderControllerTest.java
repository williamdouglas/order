package com.example.order.adapter.rest;

import com.example.order.adapter.rest.mapper.OrderRestMapper;
import com.example.order.adapter.rest.model.OrderDTO;
import com.example.order.application.domain.Order;
import com.example.order.port.in.OrderInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderRestMapper orderRestMapper;
    @Mock
    private OrderInputPort orderInputPort;
    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        OrderDTO dto = new OrderDTO();
        Order order = new Order();
        when(orderRestMapper.modelDtoToDomain(dto)).thenReturn(order);
        when(orderInputPort.create(order)).thenReturn(order);
        when(orderRestMapper.domainToModelDto(order)).thenReturn(dto);
        ResponseEntity<OrderDTO> response = orderController.create(dto);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGet() {
        Order order = new Order();
        OrderDTO dto = new OrderDTO();
        when(orderInputPort.get(1L)).thenReturn(order);
        when(orderRestMapper.domainToModelDto(order)).thenReturn(dto);
        ResponseEntity<OrderDTO> response = orderController.get(1L);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAll() {
        Order order = new Order();
        OrderDTO dto = new OrderDTO();
        when(orderInputPort.getAll()).thenReturn(List.of(order));
        when(orderRestMapper.domainToModelDto(order)).thenReturn(dto);
        ResponseEntity<List<OrderDTO>> response = orderController.getAll();
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemove() {
        doNothing().when(orderInputPort).remove(1L);
        ResponseEntity<Void> response = orderController.remove(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderInputPort, times(1)).remove(1L);
    }
}
