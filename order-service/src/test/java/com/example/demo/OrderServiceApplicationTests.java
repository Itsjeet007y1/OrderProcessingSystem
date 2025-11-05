package com.example.demo;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.OrderStatus;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void testCreateAndGetOrder() {
        Order o = new Order();
        o.setCustomerId("cust-1");
        o.setItems(Arrays.asList(new OrderItem("p1", "Product 1", 2, 10.0)));

        Order created = orderService.createOrder(o);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getStatus()).isEqualTo(OrderStatus.PENDING);

        Optional<Order> fetched = orderService.getOrder(created.getId());
        assertThat(fetched).isPresent();
        assertThat(fetched.get().getCustomerId()).isEqualTo("cust-1");
    }
}

