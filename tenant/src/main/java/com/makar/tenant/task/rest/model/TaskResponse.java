package com.makar.tenant.task.rest.model;

import com.makar.tenant.task.TaskStatus;

import java.time.Instant;

public record TaskResponse(
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
}
