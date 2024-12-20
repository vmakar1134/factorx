package com.makar.tenant.supervisor.service;

import com.makar.tenant.supervisor.Supervisor;
import com.makar.tenant.supervisor.SupervisorMapper;
import com.makar.tenant.supervisor.SupervisorRepository;
import com.makar.tenant.supervisor.rest.model.CreateSupervisorRequest;
import com.makar.tenant.supervisor.rest.model.SupervisorResponse;
import com.makar.tenant.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    private final SupervisorMapper supervisorMapper;

    public SupervisorResponse get(Long id) {
        var admin = getById(id);
        return supervisorMapper.toResponse(admin);
    }

    public List<SupervisorResponse> get() {
        return supervisorRepository.findAll().stream()
                .map(supervisorMapper::toResponse)
                .toList();
    }

    public void create(CreateSupervisorRequest request) {
        var admin = supervisorMapper.toEntity(request);
        supervisorRepository.save(admin);
    }

    public void delete(Long id) {
        supervisorRepository.deleteById(id);
    }

    private Supervisor getById(Long id) {
        return supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Supervisor.class, "id", id));
    }

}
