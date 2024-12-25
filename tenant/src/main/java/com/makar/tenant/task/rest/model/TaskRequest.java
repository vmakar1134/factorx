package com.makar.tenant.task.rest.model;

import com.makar.tenant.task.TaskStatus;

import java.time.Instant;

public record TaskRequest(
        String title,
        String description,
        Long workerId,
        Instant deadline,
        TaskStatus status,
        int priority
) {
}
