package com.example.order.adapter.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class ProductEntity {

    private Long id;
    private String name;
    private BigDecimal price;

}
