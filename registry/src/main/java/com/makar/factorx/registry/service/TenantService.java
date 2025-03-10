package com.makar.factorx.registry.service;

import com.makar.factorx.registry.domain.entity.Tenant;
import com.makar.factorx.registry.exception.EntityNotFoundException;
import com.makar.factorx.registry.mapper.TenantMapper;
import com.makar.factorx.registry.repository.TenantRepository;
import com.makar.factorx.registry.rest.model.CreateTenantRequest;
import com.makar.factorx.registry.rest.model.TenantResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        liquibaseService.runOnSchema(request.schemaName());
    }

    @Transactional
    public void delete(Long tenantId) {
        var tenant = getById(tenantId);
        tenantRepository.deleteById(tenantId);
        liquibaseService.dropSchema(tenant.schemaName());
    }

    private Tenant getById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Tenant.class, "id", id));
    }

    private List<String> getSchemas() {
        return tenantRepository.findAll().stream()
                .map(Tenant::schemaName)
                .toList();
    }

}
