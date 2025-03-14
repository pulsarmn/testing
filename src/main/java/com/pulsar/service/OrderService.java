package com.pulsar.service;

import com.pulsar.model.Order;
import com.pulsar.repository.OrderRepository;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        int orderId = orderRepository.save(order);
        return (orderId != -1) ? "Order processed successfully" : "Order processing failed";
    }
}
