package com.makar.tenant.user.admin;

import java.util.List;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.user.User;
import com.makar.tenant.user.UserRepository;
import com.makar.tenant.user.admin.rest.model.CreateSupervisorRequest;
import com.makar.tenant.user.admin.rest.model.SupervisorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSupervisorService {

    private final UserRepository userRepository;

    private final AdminSupervisorMapper adminSupervisorMapper;

    public SupervisorResponse get(Long id) {
        var admin = getById(id);
        return adminSupervisorMapper.toResponse(admin);
    }

    public List<SupervisorResponse> get() {
        return userRepository.findAll().stream()
                .map(adminSupervisorMapper::toResponse)
                .toList();
    }

    public void create(CreateSupervisorRequest request) {
        var admin = adminSupervisorMapper.toEntity(request);
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
