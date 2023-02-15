package com.makar.factorx.manager.rest.controller.api;

import com.makar.factorx.manager.rest.model.CreateTenantRequest;
import com.makar.factorx.manager.rest.model.TenantResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("tenants")
public interface TenantManagerApi {

    @PostMapping
    ResponseEntity<Void> createTenant(@RequestBody CreateTenantRequest request);

    @GetMapping("{id}")
    ResponseEntity<TenantResponse> getTenant(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<TenantResponse>> getTenants();

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteTenant(@PathVariable("id") Long id);

}
