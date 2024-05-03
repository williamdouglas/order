package com.example.order.api;

import com.example.order.model.dto.OrderDTO;
import com.example.order.producer.MessageProducer;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping
//    public Order create(@RequestBody @Valid Order order) {
//        //return orderRepository.save(order);
//        return null;
//    }

    @PostMapping()
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    //@Autowired
    //private OrderRepository orderRepository;

//    @GetMapping
//    public List<Order> list() {
////        return orderRepository.findAll();
//        return null;
//    }


//    @DeleteMapping
//    public Order delete(@RequestBody Order order) {
//        //orderRepository.delete(order);
//
//        return order;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

//    private final MessageProducer messageProducer;
//
//    @PostMapping("/send")
//    public String sendMessage(@RequestParam("message") String message) {
//        messageProducer.sendMessage("order-topic", message);
//        return "Message sent: " + message;
//    }

//    @GetMapping("/{id}/status")
//    public String getStatus(@PathVariable("id") long id) {
//        OrderStatus orderStatus = OrderStatus.getEnumByStatusId(orderRepository.getById(id).getStatus());
//
//        if (orderStatus != null) {
//            return orderStatus.getDescription();
//        }

//        return null;
//    }

}
