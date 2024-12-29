package com.makar.tenant.task.comment.rest;

import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("tasks/comments")
public interface TaskCommentApi {

    @PostMapping
    ResponseEntity<Void> createComment(@RequestBody @Valid TaskCommentRequest request);

}
