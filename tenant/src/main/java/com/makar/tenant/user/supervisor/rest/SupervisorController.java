package com.makar.tenant.user.supervisor.rest;


import com.makar.tenant.user.supervisor.SupervisorAuthService;
import com.makar.tenant.user.supervisor.rest.model.SupervisorResponse;
import com.makar.tenant.user.supervisor.rest.model.CreateSupervisorRequest;
import com.makar.tenant.user.supervisor.rest.model.LoginSupervisorRequest;
import com.makar.tenant.user.supervisor.rest.model.RefreshRequest;
import com.makar.tenant.user.supervisor.service.SupervisorService;
import com.makar.tenant.user.supervisor.rest.model.RegistrationRequest;
import com.makar.tenant.security.JwtTokenPair;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SupervisorController implements SupervisorApi {

    private final SupervisorService supervisorService;

    private final SupervisorAuthService supervisorAuthService;

    @Override
    public ResponseEntity<SupervisorResponse> getAdmin(Long id) {
        var body = supervisorService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<SupervisorResponse>> getAdmins() {
        var body = supervisorService.get();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createAdmin(CreateSupervisorRequest request) {
        supervisorService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAdmin(Long id) {
        supervisorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<JwtTokenPair> login(LoginSupervisorRequest request) {
        var jwt = supervisorAuthService.login(request);
        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<JwtTokenPair> refresh(RefreshRequest refreshRequest) {
        var jwt = supervisorAuthService.refresh(refreshRequest.refreshToken());
        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<Void> logout(String authorizationHeader) {
        supervisorAuthService.logout(authorizationHeader.substring("Bearer ".length()));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> logoutMe() {
        supervisorAuthService.logout();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> register(RegistrationRequest request) {
        supervisorAuthService.register(request);
        return ResponseEntity.noContent().build();
    }
}
