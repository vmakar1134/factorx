package com.makar.tenant.task.comment;

import java.time.Instant;

public record TaskComment(
        Long id,
        Long parentId,
        Long taskId,
        Long authorId,
        String content,
        Instant createdAt,
        Instant updatedAt
) {
}
