package com.example.demo.service;

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
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Optional<Order> getOrder(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> listOrders(Optional<OrderStatus> status) {
        if (status.isPresent()) {
            return orderRepository.findByStatus(status.get());
        }
        return orderRepository.findAll();
    }

    public Optional<Order> updateStatus(String id, OrderStatus newStatus) {
        Optional<Order> o = orderRepository.findById(id);
        if (!o.isPresent()) return Optional.empty();
        Order order = o.get();
        order.setStatus(newStatus);
        orderRepository.save(order);
        return Optional.of(order);
    }

    public boolean cancelOrder(String id) {
        Optional<Order> o = orderRepository.findById(id);
        if (!o.isPresent()) return false;
        Order order = o.get();
        if (order.getStatus() != OrderStatus.PENDING) return false;
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return true;
    }

}

