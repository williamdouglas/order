package com.example.order.adapter.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class OrderDTO {

    private Long id;
    private String status;
    private List<ProductDTO> products;

}
