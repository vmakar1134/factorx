package com.makar.tenant.user.admin.rest;


import com.makar.tenant.user.admin.rest.model.CreateSupervisorRequest;
import com.makar.tenant.user.admin.rest.model.SupervisorResponse;
import com.makar.tenant.user.supervisor.SupervisorService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminSupervisorController implements AdminSupervisorApi {

    private final SupervisorService supervisorService;

    @Override
    public ResponseEntity<SupervisorResponse> getSupervisor(Long id) {
        var body = supervisorService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<SupervisorResponse>> getSupervisors() {
        var body = supervisorService.get();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createSupervisor(CreateSupervisorRequest request) {
        supervisorService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteSupervisor(Long id) {
        supervisorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
