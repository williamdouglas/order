package com.example.order.infra.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order_collection")
public class OrderEntity {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private long id;
    private String status;
//    private String description;
//    private LocalDateTime updateDate;

}
