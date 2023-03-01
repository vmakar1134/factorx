package com.makar.tenant.user.rest;

import com.makar.tenant.user.rest.model.CreateUserRequest;
import com.makar.tenant.user.rest.model.UserResponse;
import com.makar.tenant.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> getUser(Long id) {
        var body = userService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers() {
        var body = userService.get();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createUser(CreateUserRequest request) {
        userService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
