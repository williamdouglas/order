package com.example.order.adapter.persistence;

import com.example.order.adapter.persistence.entity.OrderEntity;
import com.example.order.adapter.persistence.mapper.OrderPersistenceMapper;
import com.example.order.adapter.persistence.repository.OrderRepository;
import com.example.order.application.domain.Order;
import com.example.order.port.out.PersistenceOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Persistence adapter implementing data access operations for orders.
 * This adapter acts as a bridge between the domain layer and the database,
 * handling entity-domain object conversions and database operations.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class PersistenceAdapter implements PersistenceOutputPort {

    private final OrderPersistenceMapper orderPersistenceMapper;

    private final OrderRepository orderRepository;

    /**
     * Persists an order domain object to the database.
     *
     * @param order the order domain object to be saved
     * @return the saved order domain object with generated/updated fields
     */
    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderRepository.save(orderPersistenceMapper.domainToEntity(order));

        return orderPersistenceMapper.entityToDomain(orderEntity);
    }

    /**
     * Retrieves an order from the database by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return the order domain object, or null if not found
     */
    @Override
    public Order findById(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElse(null);

        return orderPersistenceMapper.entityToDomain(orderEntity);
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all order domain objects
     */
    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntities = orderRepository.findAll();

        return orderEntities.stream()
                .map(orderPersistenceMapper::entityToDomain)
                .toList();
    }

}
