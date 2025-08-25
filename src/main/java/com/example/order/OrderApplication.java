package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Order Management System.
 * This Spring Boot application implements a hexagonal architecture
 * for managing order operations with Kafka messaging integration.
 *
 * @author Order Management Team
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class OrderApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
