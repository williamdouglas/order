package com.example.order.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order_collection")
public class OrderEntity {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private Long id;
    private String status;
    private List<ProductEntity> products;

}
