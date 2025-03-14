package com.pulsar.service;

import com.pulsar.exception.OrderNotFoundException;
import com.pulsar.model.Order;
import com.pulsar.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private final Order correctOrder = new Order(1, "Витамин Д3", 1, BigDecimal.valueOf(250));

    @BeforeEach
    void init() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void successfulOrderProcessing() {
        // Arrange
        String expectedMessage = "Order processed successfully";
        Mockito.when(orderRepository.save(correctOrder)).thenReturn(1);

        // Act
        String actualMessage = orderService.processOrder(correctOrder);

        // Assert
        assertEquals(expectedMessage, actualMessage);
        Mockito.verify(orderRepository, Mockito.times(1)).save(correctOrder);
    }

    @Test
    void failedOrderProcessing() {
        // Arrange
        String expectedMessage = "Order processing failed";
        Mockito.when(orderRepository.save(correctOrder)).thenReturn(-1);

        // Act
        String actualMessage = orderService.processOrder(correctOrder);

        // Assert
        assertEquals(expectedMessage, actualMessage);
        Mockito.verify(orderRepository, Mockito.times(1)).save(correctOrder);
    }

    @Test
    void shouldCorrectlyCalculateTotalPrice() {
        // Arrange
        BigDecimal expectedPrice = BigDecimal.valueOf(250);
        Mockito.when(orderRepository.findById(1)).thenReturn(Optional.of(correctOrder));

        // Act
        BigDecimal actualPrice = orderService.calculateTotal(correctOrder.getId());

        // Assert
        assertEquals(expectedPrice, actualPrice);
        Mockito.verify(orderRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void shouldThrowsIfOrderNotFound() {
        // Assert
        assertThrows(OrderNotFoundException.class, () -> orderService.calculateTotal(1));
        Mockito.verify(orderRepository, Mockito.times(1)).findById(1);
    }

    @Test
    void shouldCorrectCalculateTotalPriceIfZero() {
        // Assert
        Order order1 = new Order(2, "Геркулес", 0, BigDecimal.valueOf(534));
        Mockito.when(orderRepository.findById(2)).thenReturn(Optional.of(order1));
        BigDecimal expectedTotalPrice = BigDecimal.ZERO;

        Order order2 = new Order(3, "Гречка", 3, BigDecimal.ZERO);
        Mockito.when(orderRepository.findById(3)).thenReturn(Optional.of(order2));

        // Act
        BigDecimal firstOrderTotalPrice = orderService.calculateTotal(2);
        BigDecimal secondOrderTotalPrice = orderService.calculateTotal(3);

        // Assert
        assertAll(() -> assertEquals(expectedTotalPrice, firstOrderTotalPrice),
                () -> assertEquals(expectedTotalPrice, secondOrderTotalPrice));
    }
}
