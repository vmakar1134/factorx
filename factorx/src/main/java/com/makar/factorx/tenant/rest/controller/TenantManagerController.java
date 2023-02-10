package com.makar.factorx.tenant.rest.controller;

import com.makar.factorx.tenant.rest.controller.api.TenantManagerApi;
import com.makar.factorx.tenant.rest.model.CreateTenantRequest;
import com.makar.factorx.tenant.rest.model.TenantResponse;
import com.makar.factorx.tenant.service.TenantService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TenantManagerController implements TenantManagerApi {

    private final TenantService tenantService;

    @Override
    public void createTenant(CreateTenantRequest request) {
        tenantService.create(request);
    }

    @Override
    public TenantResponse getTenant(Long id) {
        return tenantService.get(id);
    }

    @Override
    public List<TenantResponse> getTenants() {
        return tenantService.get();
    }
}
