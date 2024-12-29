package com.makar.tenant.task.comment.rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCommentRequest(
        @Min(1)
        Long parentId,

        @NotNull
        @Min(1)
        Long taskId,

        @NotNull
        @Min(1)
        Long authorId,

        @NotBlank
        String content
) {
}
