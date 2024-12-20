package com.makar.tenant.supervisor.service;

import com.makar.tenant.supervisor.Supervisor;
import com.makar.tenant.supervisor.AdminMapper;
import com.makar.tenant.supervisor.AdminRepository;
import com.makar.tenant.supervisor.rest.model.AdminResponse;
import com.makar.tenant.supervisor.rest.model.CreateAdminRequest;
import com.makar.tenant.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final AdminMapper adminMapper;

    public AdminResponse get(Long id) {
        var admin = getById(id);
        return adminMapper.toResponse(admin);
    }

    public List<AdminResponse> get() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toResponse)
                .toList();
    }

    public void create(CreateAdminRequest request) {
        var admin = adminMapper.toEntity(request);
        adminRepository.save(admin);
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    private Supervisor getById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Supervisor.class, "id", id));
    }

}
