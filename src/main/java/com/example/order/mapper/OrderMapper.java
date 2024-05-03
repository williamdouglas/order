package com.example.order.mapper;

import com.example.order.infra.entity.OrderEntity;
import com.example.order.model.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderEntity modelToEntity(OrderDTO orderDTO);

    OrderDTO entityToModel(OrderEntity orderEntity);

}
