package com.example.order.adapter.message.consumer;

import com.example.order.port.in.OrderInputPort;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MessageConsumer {

    private final OrderInputPort orderInputPort;

    @KafkaListener(topics = "order-topic", groupId = "order-group-id")
    public void listen(String data) {
        System.out.println("Received message: " + data);

        //TODO remover
        try {
            Thread.sleep(20000);
        } catch (InterruptedException ignored) {

        }

        orderInputPort.update(data);
    }

}