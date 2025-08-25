package com.example.order.port.out;

import com.example.order.application.domain.Order;

import java.util.List;

/**
 * Output port defining the contract for order persistence operations.
 * This interface abstracts the data access layer from the business logic
 * following the hexagonal architecture pattern.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
public interface PersistenceOutputPort {

    /**
     * Persists an order to the data store.
     *
     * @param order the order domain object to be saved
     * @return the saved order domain object
     */
    Order save(Order order);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return the order domain object, or null if not found
     */
    Order findById(Long id);

    /**
     * Retrieves all orders from the data store.
     *
     * @return a list of all order domain objects
     */
    List<Order> findAll();

}
