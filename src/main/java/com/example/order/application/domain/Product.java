package com.example.order.application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Domain entity representing a product within an order.
 * This class encapsulates product information following
 * Domain-Driven Design principles.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
@ToString
@Getter
@Setter
public class Product {

    /** Unique identifier for the product */
    private Long id;
    
    /** Name or description of the product */
    private String name;
    
    /** Price of the product with high precision for monetary calculations */
    private BigDecimal price;

}
