package com.makar.tenant.task.rest;

import com.makar.tenant.task.rest.model.TaskRequest;
import com.makar.tenant.task.rest.model.TaskResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("tasks")
public interface TaskApi {

    @GetMapping("{id}")
    ResponseEntity<TaskResponse> getTask(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Page<TaskResponse>> getTasks(@ParameterObject @PageableDefault Pageable pageable);

    @PostMapping
    ResponseEntity<Void> createTask(@RequestBody TaskRequest request);

    @PutMapping("{id}")
    ResponseEntity<Void> updateTask(@PathVariable("id") Long id, @RequestBody TaskRequest request);

}
