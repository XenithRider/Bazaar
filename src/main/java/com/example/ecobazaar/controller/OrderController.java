package com.example.ecobazaar.controller;


import com.example.ecobazaar.model.Order;
import com.example.ecobazaar.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService ;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Checkout (create new order)
    @PostMapping("/checkout/{userId}")
    public Order checkout(@PathVariable Long userId) {
        return orderService.checkout(userId);
    }

    // View past orders
    @GetMapping("/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

}
