package com.makar.tenant.worker.rest;

import com.makar.tenant.worker.rest.model.CreateUserRequest;
import com.makar.tenant.worker.rest.model.UserResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("users")
public interface UserApi {

    @GetMapping("{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<UserResponse>> getUsers();

    @PostMapping
    ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") Long id);

}
