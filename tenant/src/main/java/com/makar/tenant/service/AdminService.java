package com.makar.tenant.service;


import com.makar.tenant.entity.Admin;
import com.makar.tenant.mapper.AdminMapper;
import com.makar.tenant.repository.AdminRepository;
import com.makar.tenant.rest.model.AdminResponse;
import com.makar.tenant.rest.model.CreateAdminRequest;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private Admin getById(Long id) {
        return adminRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Admin not found by id: " + id));
    }
}
