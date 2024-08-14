package com.company.orderservice.dto.converter;

import com.company.orderservice.model.Order;
import com.company.orderservice.model.Outbox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderToOutboxConverter {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public Outbox toOutbox(Order order) {
        try {
            return Outbox.builder()
                    .aggregateId(order.getId().toString())
                    .payload(objectMapper.writeValueAsString(order))
                    .createdAt(LocalDateTime.now())
                    .processed(false)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
