package com.makar.tenant.user.supervisor;

import com.makar.tenant.user.User;
import com.makar.tenant.user.UserRepository;
import com.makar.tenant.user.admin.rest.model.CreateSupervisorRequest;
import com.makar.tenant.user.admin.rest.model.SupervisorResponse;
import com.makar.tenant.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    private final UserRepository userRepository;

    private final SupervisorMapper supervisorMapper;

    public SupervisorResponse get(Long id) {
        var admin = getById(id);
        return supervisorMapper.toResponse(admin);
    }

    public List<SupervisorResponse> get() {
        return userRepository.findAll().stream()
                .map(supervisorMapper::toResponse)
                .toList();
    }

    public void create(CreateSupervisorRequest request) {
        var admin = supervisorMapper.toEntity(request);
        userRepository.save(admin);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));
    }

}
