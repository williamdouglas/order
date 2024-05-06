package com.example.order.application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class Product {

    private Long id;
    private String name;
    private BigDecimal price;

}
