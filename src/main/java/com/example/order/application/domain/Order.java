package com.example.order.application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class Order {

    private Long id;
    private String status;
    private List<Product> products;

}
