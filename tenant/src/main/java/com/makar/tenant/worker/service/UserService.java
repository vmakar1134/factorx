package com.makar.tenant.worker.service;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.worker.Worker;
import com.makar.tenant.worker.UserMapper;
import com.makar.tenant.worker.UserRepository;
import com.makar.tenant.worker.rest.model.CreateUserRequest;
import com.makar.tenant.worker.rest.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse get(Long id) {
        Worker worker = getById(id);
        return userMapper.toResponse(worker);
    }

    public List<UserResponse> get() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public void create(CreateUserRequest request) {
        var user = userMapper.toEntity(request);
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private Worker getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Worker.class, "id", id));
    }

}
