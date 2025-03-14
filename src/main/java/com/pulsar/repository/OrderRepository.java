package com.pulsar.repository;

import com.pulsar.model.Order;

public class OrderRepository {

    private static int id = 1;

    public int save(Order order) {
        if (order == null || order.getProductName() == null) {
            return -1;
        }
        return ++id;
    }
}
