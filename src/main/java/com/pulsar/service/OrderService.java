package com.pulsar.service;

import com.pulsar.exception.OrderNotFoundException;
import com.pulsar.model.Order;
import com.pulsar.repository.OrderRepository;

import java.math.BigDecimal;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        int orderId = orderRepository.save(order);
        return (orderId != -1) ? "Order processed successfully" : "Order processing failed";
    }

    public BigDecimal calculateTotal(int orderId) {
        return orderRepository.findById(orderId)
                .map(Order::getTotalPrice)
                .orElseThrow(OrderNotFoundException::new);
    }
}
