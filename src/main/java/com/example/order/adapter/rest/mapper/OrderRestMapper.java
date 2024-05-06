package com.example.order.adapter.rest.mapper;

import com.example.order.adapter.rest.model.OrderDTO;
import com.example.order.application.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderRestMapper {

    Order modelDtoToDomain(OrderDTO orderDTO);

    OrderDTO domainToModelDto(Order order);

}
