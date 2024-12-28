package com.makar.tenant.task.comment.rest.model;

import java.time.Instant;

public record CommentRequest(
        Long parentId,
        Long taskId,
        Long authorId,
        String content,
        Instant createdAt,
        Instant updatedAt
) {
}
