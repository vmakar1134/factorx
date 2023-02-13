package com.makar.factorx.tenant.service;

import com.makar.factorx.tenant.domain.Tenant;
import com.makar.factorx.tenant.mapper.TenantMapper;
import com.makar.factorx.tenant.repository.TenantRepository;
import com.makar.factorx.tenant.rest.model.CreateTenantRequest;
import com.makar.factorx.tenant.rest.model.TenantResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    private final TenantMapper tenantMapper;

    public List<TenantResponse> get() {
        return tenantRepository.findAll().stream()
            .map(tenantMapper::toResponse)
            .toList();
    }

    public TenantResponse get(Long id) {
        var tenant = getById(id);
        return tenantMapper.toResponse(tenant);
    }

    public void create(CreateTenantRequest request) {
        var tenant = tenantMapper.toEntity(request);
        tenantRepository.save(tenant);
    }

    private Tenant getById(Long id) {
        return tenantRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tenant not found by id: " + id));
    }
}
