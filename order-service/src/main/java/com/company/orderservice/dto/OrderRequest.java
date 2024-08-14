package com.company.orderservice.dto;

import com.company.orderservice.model.Order;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderRequest(
        String name,
        String customerId,
        String productType,
        int quantity,
        BigDecimal price
) {
    public Order toOrder() {
        return Order.builder()
                .name(name)
                .customerId(customerId)
                .productType(productType)
                .quantity(quantity)
                .price(price)
                .orderDate(LocalDateTime.now())
                .build();
    }
}
