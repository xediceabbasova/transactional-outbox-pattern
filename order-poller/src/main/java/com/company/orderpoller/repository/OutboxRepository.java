package com.company.orderpoller.repository;

import com.company.orderpoller.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findByProcessedFalse();
}
