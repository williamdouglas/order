package com.example.order.adapter.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;

}
