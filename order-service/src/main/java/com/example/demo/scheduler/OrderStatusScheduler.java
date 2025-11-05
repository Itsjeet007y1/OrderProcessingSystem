package com.example.demo.scheduler;

import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStatusScheduler {

    @Autowired
    private OrderRepository orderRepository;

    // Runs every 5 minutes and moves PENDING orders to PROCESSING
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void promotePendingOrders() {
        List<Order> pending = orderRepository.findByStatus(OrderStatus.PENDING);
        for (Order o : pending) {
            o.setStatus(OrderStatus.PROCESSING);
            orderRepository.save(o);
        }
    }
}

