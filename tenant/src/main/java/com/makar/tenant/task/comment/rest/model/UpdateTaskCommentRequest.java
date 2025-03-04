package com.makar.tenant.task.comment.rest.model;

import jakarta.validation.constraints.NotBlank;

public record UpdateTaskCommentRequest(

        @NotBlank
        String content
) {
}
