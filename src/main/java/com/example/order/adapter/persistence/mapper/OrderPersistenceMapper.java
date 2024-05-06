package com.example.order.adapter.persistence.mapper;

import com.example.order.adapter.persistence.entity.OrderEntity;
import com.example.order.application.domain.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    OrderEntity domainToEntity(Order order);

    Order entityToDomain(OrderEntity orderEntity);

}
