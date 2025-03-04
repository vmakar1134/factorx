package com.makar.tenant.task.comment;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;

@RedisHash("task:comment")
public record TaskComment(
        @Id
        Long id,
        Long parentId,
        Long taskId,
        Long authorId,
        String content,
        Instant createdAt,
        Instant updatedAt
) {
}
