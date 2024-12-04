package com.makar.tenant.admin.rest;


import com.makar.tenant.admin.rest.model.AdminResponse;
import com.makar.tenant.admin.rest.model.CreateAdminRequest;
import com.makar.tenant.admin.rest.model.LoginAdminRequest;
import com.makar.tenant.admin.rest.model.RegisterAdminRequest;
import com.makar.tenant.admin.service.AdminAuthService;
import com.makar.tenant.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    private final AdminAuthService adminAuthService;

    @Override
    public ResponseEntity<AdminResponse> getAdmin(Long id) {
        var body = adminService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<AdminResponse>> getAdmins() {
        var body = adminService.get();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createAdmin(CreateAdminRequest request) {
        adminService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAdmin(Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<String> login(LoginAdminRequest request) {
        var jwt = adminAuthService.login(request);
        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<Void> register(RegisterAdminRequest request) {
        adminAuthService.register(request);
        return ResponseEntity.noContent().build();
    }
}
