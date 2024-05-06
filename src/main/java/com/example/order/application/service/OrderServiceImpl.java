package com.example.order.application.service;

import com.example.order.adapter.persistence.entity.OrderEntity;
import com.example.order.application.domain.Order;
import com.example.order.application.enums.OrderStatus;
import com.example.order.application.exception.ProductNotFoundException;
import com.example.order.application.exception.RestExceptionHandler;
import com.example.order.port.out.SequenceGeneratorOutputPort;
import com.example.order.port.in.OrderInputPort;
import com.example.order.port.out.PersistenceOutputPort;
import com.example.order.port.out.ProducerOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderInputPort {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final PersistenceOutputPort persistenceOutputPort;

    private final SequenceGeneratorOutputPort sequenceGeneratorOutputPort;

    private final ProducerOutputPort producerOutputPort;

    @Override
    public Order create(Order order) {
        order.setId(sequenceGeneratorOutputPort.generateSequence(OrderEntity.SEQUENCE_NAME));
        order.setStatus(OrderStatus.AGUARDANDO_ENVIO.name());

        try {
            order = persistenceOutputPort.save(order);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao gravar dados do pedido.", e);
        }

        postMessageKafka(order);

        return order;
    }

    private void postMessageKafka(Order order) {
        try {
            producerOutputPort.sendMessage("order-topic", order);
        } catch (JsonProcessingException e) {
            throw new RestExceptionHandler("Erro ao fazer o parse do objeto.", e);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao postar dados no kafka.", e);
        }
    }

    @Override
    public Order get(Long id) {
        Order order;

        try {
            order = persistenceOutputPort.findById(id);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao buscar dados do pedido.", e);
        }

        if (order == null) {
            throw new ProductNotFoundException("Pedido nao encontrado com Id: " + id);
        }

        return order;
    }

    @Override
    public void update(String orderString) {
        Order order;

        try {
            order = new ObjectMapper().readValue(orderString, Order.class);
            order.setStatus(OrderStatus.ENVIADO_TRANSPORTADORA.name());
        } catch (JsonProcessingException e) {
            throw new RestExceptionHandler("Erro ao consumir dados do pedido no kafka.", e);
        }

        update(order);
    }

    @Override
    public Order update(Order order) {
        try {
            return persistenceOutputPort.save(order);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao atualizar status do pedido.", e);
        }
    }

}
