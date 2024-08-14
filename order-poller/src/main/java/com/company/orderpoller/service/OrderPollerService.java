package com.company.orderpoller.service;

import com.company.orderpoller.model.Outbox;
import com.company.orderpoller.producer.KafkaProducer;
import com.company.orderpoller.repository.OutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class OrderPollerService {

    private final OutboxRepository outboxRepository;
    private final KafkaProducer kafkaProducer;

    public OrderPollerService(OutboxRepository outboxRepository, KafkaProducer kafkaProducer) {
        this.outboxRepository = outboxRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(fixedRate = 60000)
    public void pollOutboxMessagesAndPublish() {
        List<Outbox> unprocessedRecords = outboxRepository.findByProcessedFalse();
        log.info("unprocessed record count : {}", unprocessedRecords.size());

        unprocessedRecords.forEach(outbox -> {
                    try {
                        kafkaProducer.send(outbox.getPayload());
                        outbox.setProcessed(true);
                        outboxRepository.save(outbox);
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                }
        );
    }
}
