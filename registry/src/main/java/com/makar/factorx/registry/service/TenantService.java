package com.makar.factorx.registry.service;

import com.makar.factorx.registry.domain.entity.Tenant;
import com.makar.factorx.registry.mapper.TenantMapper;
import com.makar.factorx.registry.repository.TenantRepository;
import com.makar.factorx.registry.rest.model.CreateTenantRequest;
import com.makar.factorx.registry.rest.model.TenantResponse;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    private final TenantMapper tenantMapper;

    private final LiquibaseService liquibaseService;

    @PostConstruct
    void init() {
        var schemas = getSchemas();
        liquibaseService.runOnSchemas(schemas);
    }

    public List<TenantResponse> get() {
        return tenantRepository.findAll().stream()
            .map(tenantMapper::toResponse)
            .toList();
    }

    public TenantResponse get(Long id) {
        var tenant = getById(id);
        return tenantMapper.toResponse(tenant);
    }

    @Transactional
    public void create(CreateTenantRequest request) {
        var tenant = tenantMapper.toEntity(request);
        tenantRepository.save(tenant);
        liquibaseService.runOnSchema(request.getSchemaName());
    }

    public void delete(Long tenantId) {
        var tenant = getById(tenantId);
        tenantRepository.deleteById(tenantId);
        liquibaseService.dropSchema(tenant.getSchemaName());
    }

    private Tenant getById(Long id) {
        return tenantRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tenant not found by id: " + id));
    }

    private List<String> getSchemas() {
        return tenantRepository.findAll().stream()
            .map(Tenant::getSchemaName)
            .toList();
    }
}
