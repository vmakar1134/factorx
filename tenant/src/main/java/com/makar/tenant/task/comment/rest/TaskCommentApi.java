package com.makar.tenant.task.comment.rest;

import com.makar.tenant.task.comment.TaskComment;
import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import com.makar.tenant.task.comment.rest.model.UpdateTaskCommentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("tasks/comments")
public interface TaskCommentApi {

    @PostMapping
    ResponseEntity<Void> createComment(@RequestBody @Valid TaskCommentRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteComment(@PathVariable("id") Long id);

    @PutMapping("{id}")
    ResponseEntity<Void> updateComment(@PathVariable Long id, @RequestBody UpdateTaskCommentRequest request);

    @GetMapping()
    ResponseEntity<List<TaskComment>> getComments(@RequestParam("taskId") Long taskId);
}
