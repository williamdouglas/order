package com.example.order.adapter.persistence;

import com.example.order.adapter.persistence.entity.OrderEntity;
import com.example.order.adapter.persistence.mapper.OrderPersistenceMapper;
import com.example.order.adapter.persistence.repository.OrderRepository;
import com.example.order.application.domain.Order;
import com.example.order.port.out.PersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersistenceAdapter implements PersistenceService {

    private final OrderPersistenceMapper orderPersistenceMapper;

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderRepository.save(orderPersistenceMapper.domainToEntity(order));

        return orderPersistenceMapper.entityToDomain(orderEntity);
    }

    @Override
    public Order findById(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElse(null);

        return orderPersistenceMapper.entityToDomain(orderEntity);
    }

}
