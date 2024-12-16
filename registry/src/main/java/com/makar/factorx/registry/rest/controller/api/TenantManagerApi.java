package com.makar.factorx.registry.rest.controller.api;

import com.makar.factorx.registry.rest.model.CreateAdminRequest;
import com.makar.factorx.registry.rest.model.CreateTenantRequest;
import com.makar.factorx.registry.rest.model.TenantResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("tenants")
public interface TenantManagerApi {

    @PostMapping
    ResponseEntity<Void> createTenant(@RequestBody CreateTenantRequest request);

    @PostMapping("{id}/admins")
    ResponseEntity<Void> createAdmin(@PathVariable Long id, @RequestBody CreateAdminRequest request);

    @GetMapping("{id}")
    ResponseEntity<TenantResponse> getTenant(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<TenantResponse>> getTenants();

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteTenant(@PathVariable("id") Long id);

}
