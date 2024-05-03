package com.example.order.consumer;

import com.example.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MessageConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "order-topic", groupId = "order-group-id")
    public void listen(String data) {
        System.out.println("Received message: " + data);
        orderService.update(data);
    }

}