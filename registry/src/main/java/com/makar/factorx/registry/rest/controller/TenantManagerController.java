package com.makar.factorx.registry.rest.controller;

import com.makar.factorx.registry.rest.controller.api.TenantManagerApi;
import com.makar.factorx.registry.rest.model.CreateAdminRequest;
import com.makar.factorx.registry.rest.model.CreateTenantRequest;
import com.makar.factorx.registry.rest.model.TenantResponse;
import com.makar.factorx.registry.service.AdminService;
import com.makar.factorx.registry.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TenantManagerController implements TenantManagerApi {

    private final TenantService tenantService;

    private final AdminService adminService;

    @Override
    public ResponseEntity<Void> createTenant(CreateTenantRequest request) {
        tenantService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> createAdmin(Long id, CreateAdminRequest request) {
        adminService.create(id, request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TenantResponse> getTenant(Long id) {
        var body = tenantService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<TenantResponse>> getTenants() {
        var body = tenantService.get();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> deleteTenant(Long id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
