package com.example.order.port.in;

import com.example.order.application.domain.Order;

import java.util.List;

/**
 * Input port defining the contract for order management operations.
 * This interface represents the entry point to the application's core business logic
 * following the hexagonal architecture pattern.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
public interface OrderInputPort {

    /**
     * Creates a new order in the system.
     *
     * @param order the order domain object to be created
     * @return the created order with generated ID and initial status
     */
    Order create(Order order);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return the order domain object
     */
    Order get(Long id);

    /**
     * Retrieves all orders from the system.
     *
     * @return a list of all order domain objects
     */
    List<Order> getAll();

    /**
     * Updates order status from a JSON string representation (typically from Kafka).
     *
     * @param order JSON string representation of the order
     */
    void update(String order);

    /**
     * Updates an existing order.
     *
     * @param order the order domain object to be updated
     * @return the updated order domain object
     */
    Order update(Order order);

}
