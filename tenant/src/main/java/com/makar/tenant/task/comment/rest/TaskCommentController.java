package com.makar.tenant.task.comment.rest;

import com.makar.tenant.task.comment.TaskCommentService;
import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskCommentController implements TaskCommentApi {

    private final TaskCommentService taskCommentService;


    @Override
    public ResponseEntity<Void> createComment(TaskCommentRequest request) {
        taskCommentService.create(request);
        return ResponseEntity.ok().build();
    }
}
