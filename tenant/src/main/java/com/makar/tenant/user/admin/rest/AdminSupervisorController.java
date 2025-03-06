package com.makar.tenant.user.admin.rest;


import java.util.List;

import com.makar.tenant.user.admin.AdminSupervisorService;
import com.makar.tenant.user.admin.rest.model.CreateSupervisorRequest;
import com.makar.tenant.user.admin.rest.model.SupervisorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminSupervisorController implements AdminSupervisorApi {

    private final AdminSupervisorService adminSupervisorService;

    @Override
    public ResponseEntity<SupervisorResponse> getSupervisor(Long id) {
        var body = adminSupervisorService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<SupervisorResponse>> getSupervisors() {
        var body = adminSupervisorService.get();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createSupervisor(CreateSupervisorRequest request) {
        adminSupervisorService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteSupervisor(Long id) {
        adminSupervisorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
