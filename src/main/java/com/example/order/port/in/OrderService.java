package com.example.order.port.in;

import com.example.order.application.domain.Order;

public interface OrderService {

    Order create(Order order);

    Order get(Long id);

    void update(String order);

    Order update(Order order);

}
