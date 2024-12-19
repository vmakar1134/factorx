package com.makar.tenant.admin.rest;


import com.makar.tenant.admin.rest.model.AdminResponse;
import com.makar.tenant.admin.rest.model.CreateAdminRequest;
import com.makar.tenant.admin.rest.model.LoginAdminRequest;
import com.makar.tenant.admin.rest.model.RefreshRequest;
import com.makar.tenant.admin.service.AdminAuthService;
import com.makar.tenant.admin.service.AdminService;
import com.makar.tenant.admin.rest.model.RegistrationRequest;
import com.makar.tenant.security.JwtTokenPair;
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
    public ResponseEntity<JwtTokenPair> login(LoginAdminRequest request) {
        var jwt = adminAuthService.login(request);
        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<JwtTokenPair> refresh(RefreshRequest refreshRequest) {
        var jwt = adminAuthService.refresh(refreshRequest.refreshToken());
        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<Void> logout(String authorizationHeader) {
        adminAuthService.logout(authorizationHeader.substring("Bearer ".length()));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> logoutMe() {
        adminAuthService.logout();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> register(RegistrationRequest request) {
        adminAuthService.register(request);
        return ResponseEntity.noContent().build();
    }
}
