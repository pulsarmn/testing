package com.pulsar.repository;

import com.pulsar.model.Order;

import java.math.BigDecimal;
import java.util.Optional;

public class OrderRepository {

    private static int id = 1;

    public int save(Order order) {
        if (order == null || order.getProductName() == null) {
            return -1;
        }
        return ++id;
    }

    public Optional<Order> findById(Integer orderId) {
        if (orderId < 1) {
            return Optional.empty();
        }
        return Optional.of(new Order(orderId, "TEST", 10, BigDecimal.valueOf(100)));
    }
}
