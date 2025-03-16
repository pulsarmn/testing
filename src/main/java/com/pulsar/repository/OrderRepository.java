package com.pulsar.repository;

import com.pulsar.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderRepository {

    private static int id = 1;
    private final Map<Integer, Order> orders;

    public OrderRepository() {
        this.orders = new HashMap<>();
    }

    public int save(Order order) {
        if (order == null) {
            return -1;
        }
        orders.put(id, order);
        return id++;
    }

    public Optional<Order> findById(Integer orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }
}
