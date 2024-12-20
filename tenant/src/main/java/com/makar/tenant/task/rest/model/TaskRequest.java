package com.makar.tenant.task.rest.model;

import java.time.Instant;

public record TaskRequest(
        String title,
        String description,
        Long workerId,
        Instant deadline,
        int priority
) {
}
