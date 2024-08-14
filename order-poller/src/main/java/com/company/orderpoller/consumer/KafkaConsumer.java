package com.company.orderpoller.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "unprocessed-order-events", groupId = "jt-group")
    public void consume(String message) {
        log.info("Event consumed {}", message);
    }
}
