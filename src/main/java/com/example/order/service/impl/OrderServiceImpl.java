package com.example.order.service.impl;

import com.example.order.enums.OrderStatus;
import com.example.order.infra.entity.OrderEntity;
import com.example.order.infra.repository.OrderRepository;
import com.example.order.mapper.OrderMapper;
import com.example.order.model.dto.OrderDTO;
import com.example.order.producer.MessageProducer;
import com.example.order.service.OrderService;
import com.example.order.service.SequenceGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    private final MessageProducer messageProducer;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        orderDTO.setId(sequenceGeneratorService.generateSequence(OrderEntity.SEQUENCE_NAME));
        orderDTO.setStatus(OrderStatus.AGUARDANDO_ENVIO.name());
        OrderEntity orderEntity = orderRepository.save(orderMapper.modelToEntity(orderDTO));

        orderDTO = orderMapper.entityToModel(orderEntity);

        try {
            messageProducer.sendMessage("order-topic", orderDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        return orderDTO;
    }

    @Override
    public OrderDTO get(long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElse(null);

        return orderMapper.entityToModel(orderEntity);
    }

    @Override
    public void update(String orderString) {
        OrderDTO orderDTO;

        try {
            orderDTO = new ObjectMapper().readValue(orderString, OrderDTO.class);
            orderDTO.setStatus(OrderStatus.ENVIADO_TRANSPORTADORA.name());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

        update(orderDTO);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        OrderEntity orderEntity = orderRepository.save(orderMapper.modelToEntity(orderDTO));

        return orderMapper.entityToModel(orderEntity);
    }

}
