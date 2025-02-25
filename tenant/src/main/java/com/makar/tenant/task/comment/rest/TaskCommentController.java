package com.makar.tenant.task.comment.rest;

import com.makar.tenant.task.comment.TaskComment;
import com.makar.tenant.task.comment.TaskCommentService;
import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import com.makar.tenant.task.comment.rest.model.UpdateTaskCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskCommentController implements TaskCommentApi {

    private final TaskCommentService taskCommentService;

    @Override
    public ResponseEntity<Void> createComment(TaskCommentRequest request) {
        taskCommentService.create(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long id) {
        taskCommentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateComment(Long id, UpdateTaskCommentRequest request) {
        taskCommentService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TaskComment>> getComments(Long taskId) {
        List<TaskComment> comments = taskCommentService.getComments(taskId);
        return ResponseEntity.ok(comments);
    }
}
