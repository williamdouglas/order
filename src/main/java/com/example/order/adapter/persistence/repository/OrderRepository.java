package com.example.order.adapter.persistence.repository;

import com.example.order.adapter.persistence.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
}
