package com.company.orderservice.service;

import com.company.orderservice.dto.OrderRequest;
import com.company.orderservice.dto.converter.OrderToOutboxConverter;
import com.company.orderservice.model.Order;
import com.company.orderservice.repository.OrderRepository;
import com.company.orderservice.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final OrderToOutboxConverter orderToOutboxConverter;

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository, OrderToOutboxConverter converter) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
        this.orderToOutboxConverter = converter;
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        Order order = orderRepository.save(orderRequest.toOrder());
        outboxRepository.save(orderToOutboxConverter.toOutbox(order));
        return order;
    }
}
