package com.makar.factorx.registry.service;

import com.makar.factorx.registry.rest.model.CreateAdminRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final TenantService tenantService;

    public void create(Long tenantId, CreateAdminRequest request) {
        var schemaName = tenantService.get(tenantId).tenantName();
        // create admin
    }

}
