package com.example.order.adapter.persistence;

import com.example.order.adapter.persistence.entity.OrderEntity;
import com.example.order.adapter.persistence.mapper.OrderPersistenceMapper;
import com.example.order.adapter.persistence.repository.OrderRepository;
import com.example.order.application.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersistenceAdapterTest {
    @Mock
    private OrderPersistenceMapper orderPersistenceMapper;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private PersistenceAdapter persistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Order order = new Order();
        OrderEntity entity = new OrderEntity();
        when(orderPersistenceMapper.domainToEntity(order)).thenReturn(entity);
        when(orderRepository.save(entity)).thenReturn(entity);
        when(orderPersistenceMapper.entityToDomain(entity)).thenReturn(order);
        Order result = persistenceAdapter.save(order);
        assertEquals(order, result);
    }

    @Test
    void testFindById() {
        OrderEntity entity = new OrderEntity();
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(orderPersistenceMapper.entityToDomain(entity)).thenReturn(order);
        Order result = persistenceAdapter.findById(1L);
        assertEquals(order, result);
    }

    @Test
    void testFindAll() {
        OrderEntity entity = new OrderEntity();
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(entity));
        when(orderPersistenceMapper.entityToDomain(entity)).thenReturn(order);
        List<Order> result = persistenceAdapter.findAll();
        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
    }

    @Test
    void testRemove() {
        doNothing().when(orderRepository).deleteById(1L);
        persistenceAdapter.remove(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
