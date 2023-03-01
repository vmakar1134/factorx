package com.makar.tenant.user.rest;

import com.makar.tenant.user.rest.model.CreateUserRequest;
import com.makar.tenant.user.rest.model.UserResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
