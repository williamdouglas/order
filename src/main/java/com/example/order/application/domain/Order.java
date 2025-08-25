package com.example.order.application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Domain entity representing an order in the system.
 * This class encapsulates all the business logic and data related to an order,
 * following the Domain-Driven Design principles.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
@ToString
@Getter
@Setter
public class Order {

    /** Unique identifier for the order */
    private Long id;
    
    /** Current status of the order (e.g., AGUARDANDO_ENVIO, ENVIADO_TRANSPORTADORA) */
    private String status;
    
    /** List of products included in this order */
    private List<Product> products;

}
