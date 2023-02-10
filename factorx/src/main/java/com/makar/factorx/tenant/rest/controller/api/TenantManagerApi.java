package com.makar.factorx.tenant.rest.controller.api;

import com.makar.factorx.tenant.rest.model.CreateTenantRequest;
import com.makar.factorx.tenant.rest.model.TenantResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tenants")
public interface TenantManagerApi {

    @PostMapping
    void createTenant(@RequestBody CreateTenantRequest request);

    @GetMapping("{id}")
    TenantResponse getTenant(@PathVariable("id") Long id);

    @GetMapping
    List<TenantResponse> getTenants();

}
