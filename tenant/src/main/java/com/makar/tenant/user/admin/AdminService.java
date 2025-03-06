package com.makar.tenant.user.admin;

import com.makar.tenant.user.UserRepository;
import com.makar.tenant.user.admin.rest.model.AdminResponse;
import com.makar.tenant.user.admin.rest.model.CreateAdminRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository adminRepository;

    private final AdminMapper adminMapper;

    public AdminResponse get(Long id) {
        return adminRepository.findById(id)
                .map(adminMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));
    }

    public Page<AdminResponse> getAll(Pageable pageable) {
        return adminRepository.findAll(pageable)
                .map(adminMapper::toResponse);
    }

    public void create(CreateAdminRequest request) {
        if (adminRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        var admin = adminMapper.toEntity(request);
        adminRepository.save(admin);
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }
}
