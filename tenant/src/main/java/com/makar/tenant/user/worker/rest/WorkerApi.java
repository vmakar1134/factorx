package com.makar.tenant.user.worker.rest;

import com.makar.tenant.user.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.user.worker.rest.model.WorkerResponse;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("users")
public interface WorkerApi {

    @GetMapping("{id}")
    ResponseEntity<WorkerResponse> getUser(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<WorkerResponse>> getUsers();

    @PostMapping
    ResponseEntity<Void> createUser(@RequestBody CreateWorkerRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") Long id);

}
