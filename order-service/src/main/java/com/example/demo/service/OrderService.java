package com.example.demo.service;

import com.example.demo.exception.InvalidOrderException;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        if (order == null || order.getItems() == null || order.getItems().isEmpty()) {
            throw new InvalidOrderException("Order must contain at least one item");
        }
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(String id) {
        return Optional.ofNullable(orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id)));
    }

    public List<Order> listOrders(Optional<OrderStatus> status) {
        if (status.isPresent()) {
            return orderRepository.findByStatus(status.get());
        }
        return orderRepository.findAll();
    }

    public Optional<Order> updateStatus(String id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (newStatus == null) throw new InvalidOrderException("Status must be provided");
        order.setStatus(newStatus);
        orderRepository.save(order);
        return Optional.of(order);
    }

    public boolean cancelOrder(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (order.getStatus() != OrderStatus.PENDING) throw new InvalidOrderException("Only pending orders can be cancelled");
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return true;
    }

}
