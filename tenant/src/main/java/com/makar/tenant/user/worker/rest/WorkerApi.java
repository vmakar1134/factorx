package com.makar.tenant.user.worker.rest;

import com.makar.tenant.user.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.user.worker.rest.model.WorkerResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("workers")
public interface WorkerApi {

    @GetMapping("{id}")
    ResponseEntity<WorkerResponse> getUser(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Page<WorkerResponse>> getUsers(@ParameterObject @PageableDefault Pageable pageable);

    @PostMapping
    ResponseEntity<Void> createUser(@RequestBody CreateWorkerRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") Long id);

}
