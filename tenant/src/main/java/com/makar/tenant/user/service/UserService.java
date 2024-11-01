package com.makar.tenant.user.service;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.user.entity.User;
import com.makar.tenant.user.mapper.UserMapper;
import com.makar.tenant.user.repository.UserRepository;
import com.makar.tenant.user.rest.model.CreateUserRequest;
import com.makar.tenant.user.rest.model.UserResponse;
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
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));
    }
}
