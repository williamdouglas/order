package com.example.order.port.out;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProducerOutputPort {

    void sendMessage(String topic, Object data) throws JsonProcessingException;

}
