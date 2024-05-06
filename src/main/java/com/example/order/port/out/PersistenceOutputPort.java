package com.example.order.port.out;

import com.example.order.application.domain.Order;

public interface PersistenceOutputPort {

    Order save(Order order);

    Order findById(Long id);

}
