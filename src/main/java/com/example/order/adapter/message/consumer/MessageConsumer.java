package com.example.order.adapter.message.consumer;

import com.example.order.port.in.OrderService;
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

        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignored) {

        }

       orderService.update(data);
    }

}