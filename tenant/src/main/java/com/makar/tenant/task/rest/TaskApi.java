package com.makar.tenant.task.rest;

import com.makar.tenant.task.rest.model.TaskRequest;
import com.makar.tenant.task.rest.model.TaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("tasks")
public interface TaskApi {

    @GetMapping("{id}")
    ResponseEntity<TaskResponse> getTask(@PathVariable("id") Long id);

    @PostMapping
    ResponseEntity<Void> createTask(@RequestBody TaskRequest request);
}
