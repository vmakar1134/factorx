package com.makar.factorx.admin.service;



import com.makar.factorx.admin.entity.Admin;
import com.makar.factorx.admin.mapper.AdminMapper;
import com.makar.factorx.admin.repository.AdminRepository;
import com.makar.factorx.admin.rest.model.AdminResponse;
import com.makar.factorx.admin.rest.model.CreateAdminRequest;
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
