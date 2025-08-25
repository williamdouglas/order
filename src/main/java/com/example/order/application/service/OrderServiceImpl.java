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

import java.util.List;

/**
 * Implementation of OrderInputPort providing core business logic for order management.
 * This service handles order creation, retrieval, and updates while orchestrating
 * interactions with persistence, messaging, and sequence generation components.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderInputPort {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final PersistenceOutputPort persistenceOutputPort;

    private final SequenceGeneratorOutputPort sequenceGeneratorOutputPort;

    private final ProducerOutputPort producerOutputPort;

    /**
     * Creates a new order with generated ID and initial status.
     * The order is persisted and a message is sent to Kafka for further processing.
     *
     * @param order the order domain object to be created
     * @return the created order with generated ID and initial status
     * @throws RestExceptionHandler if there's an error during order persistence or Kafka messaging
     */
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

    /**
     * Sends order information to Kafka topic for asynchronous processing.
     *
     * @param order the order to be sent to Kafka
     * @throws RestExceptionHandler if there's an error during JSON processing or Kafka messaging
     */
    private void postMessageKafka(Order order) {
        try {
            producerOutputPort.sendMessage("order-topic", order);
        } catch (JsonProcessingException e) {
            throw new RestExceptionHandler("Erro ao fazer o parse do objeto.", e);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao postar dados no kafka.", e);
        }
    }

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the unique identifier of the order
     * @return the order domain object
     * @throws ProductNotFoundException if no order is found with the given ID
     * @throws RestExceptionHandler if there's an error during order retrieval
     */
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

    /**
     * Retrieves all orders from the system.
     *
     * @return a list of all order domain objects
     * @throws RestExceptionHandler if there's an error during orders retrieval
     */
    @Override
    public List<Order> getAll() {
        try {
            return persistenceOutputPort.findAll();
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao buscar todos os pedidos.", e);
        }
    }

    /**
     * Updates order status from Kafka message consumption.
     * Deserializes the order string and updates status to ENVIADO_TRANSPORTADORA.
     *
     * @param orderString JSON string representation of the order
     * @throws RestExceptionHandler if there's an error during JSON processing or order update
     */
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

    /**
     * Updates an existing order by persisting the changes.
     *
     * @param order the order domain object to be updated
     * @return the updated order domain object
     * @throws RestExceptionHandler if there's an error during order update
     */
    @Override
    public Order update(Order order) {
        try {
            return persistenceOutputPort.save(order);
        } catch (Exception e) {
            throw new RestExceptionHandler("Erro ao atualizar status do pedido.", e);
        }
    }

}
