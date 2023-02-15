package com.makar.factorx.admin.rest.controller;

import com.makar.factorx.admin.rest.controller.api.TenantManagerApi;
import com.makar.factorx.admin.rest.model.CreateTenantRequest;
import com.makar.factorx.admin.rest.model.TenantResponse;
import com.makar.factorx.admin.service.TenantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TenantManagerController implements TenantManagerApi {

    private final TenantService tenantService;

    @Override
    public ResponseEntity<Void> createTenant(CreateTenantRequest request) {
        tenantService.create(request);
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
