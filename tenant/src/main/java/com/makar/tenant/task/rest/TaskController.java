package com.makar.tenant.task.rest;

import com.makar.tenant.task.TaskService;
import com.makar.tenant.task.rest.model.TaskRequest;
import com.makar.tenant.task.rest.model.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<TaskResponse> getTask(Long id) {
        var task = taskService.get(id);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<Void> createTask(TaskRequest request) {
        taskService.create(request);
        return ResponseEntity.noContent().build();
    }
}
