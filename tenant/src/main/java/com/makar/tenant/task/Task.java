package com.makar.tenant.task;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

import static java.util.Objects.requireNonNullElse;

@Table
public record Task(
        @Id
        Long id,
        String title,
        String description,
        Long workerId,
        Long supervisorId,
        TaskStatus status,
        int priority,
        Instant deadline,
        Instant createdAt
) {

    public Task {
        status = requireNonNullElse(status, TaskStatus.NEW);
        createdAt = Instant.now();
    }
}
