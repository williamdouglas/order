package com.example.order.service;

import com.example.order.model.dto.OrderDTO;

public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);

    OrderDTO get(long id);

    void update(String order);

    OrderDTO update(OrderDTO orderDTO);

}
