package com.example.order.application.service;

import com.example.order.adapter.persistence.entity.OrderEntity;
import com.example.order.application.domain.Order;
import com.example.order.application.enums.OrderStatus;
import com.example.order.application.exception.ProductNotFoundException;
import com.example.order.application.exception.RestExceptionHandler;
import com.example.order.port.SequenceGeneratorService;
import com.example.order.port.in.OrderService;
import com.example.order.port.out.PersistenceService;
import com.example.order.port.out.ProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final PersistenceService persistenceService;

    private final SequenceGeneratorService sequenceGeneratorService;

    private final ProducerService producerService;

    @Override
    public Order create(Order order) {
        order.setId(sequenceGeneratorService.generateSequence(OrderEntity.SEQUENCE_NAME));
        order.setStatus(OrderStatus.AGUARDANDO_ENVIO.name());

        try {
            order = persistenceService.save(order);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao gravar dados do pedido.", e);
        }

        postMessageKafka(order);

        return order;
    }

    private void postMessageKafka(Order order) {
        try {
            producerService.sendMessage("order-topic", order);
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
            order = persistenceService.findById(id);
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
            return persistenceService.save(order);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao atualizar status do pedido.", e);
        }
    }

}
