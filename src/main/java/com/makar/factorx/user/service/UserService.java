package com.makar.factorx.user.service;

import com.makar.factorx.user.entity.User;
import com.makar.factorx.user.mapper.UserMapper;
import com.makar.factorx.user.repository.UserRepository;
import com.makar.factorx.user.rest.model.CreateUserRequest;
import com.makar.factorx.user.rest.model.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse get(Long id) {
        User user = getById(id);
        return userMapper.toResponse(user);
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

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found by id: {} " + id));
    }
}
