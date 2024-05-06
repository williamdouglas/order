package com.example.order.application.service;

import com.example.order.application.domain.Order;
import com.example.order.application.domain.Product;
import com.example.order.application.enums.OrderStatus;
import com.example.order.port.SequenceGeneratorService;
import com.example.order.port.out.PersistenceService;
import com.example.order.port.out.ProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Mock
    private PersistenceService persistenceService;

    @Mock
    private ProducerService producerService;

    @BeforeEach
    void setUp() {
        Mockito.when(sequenceGeneratorService.generateSequence(Mockito.any())).thenReturn(1L);
    }

    @Test
    void createSuccessTest() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(10.0));

        List<Product> products = new ArrayList<>();
        products.add(product);

        Order order = new Order();
        order.setProducts(products);

        Mockito.when(persistenceService.save(Mockito.any())).thenReturn(order);

        assertDoesNotThrow(() -> orderService.create(order));
        assertNotNull(orderService.create(order).getId());
        assertEquals(orderService.create(order).getStatus(), OrderStatus.AGUARDANDO_ENVIO.name());
    }

}