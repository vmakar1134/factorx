package com.makar.tenant.user.rest;

import com.makar.tenant.security.JwtTokenPair;
import com.makar.tenant.user.AuthService;
import com.makar.tenant.user.rest.model.LoginRequest;
import com.makar.tenant.user.rest.model.RegistrationRequest;
import com.makar.tenant.user.rest.model.RefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<JwtTokenPair> login(LoginRequest request) {
        var jwt = authService.login(request);
        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<JwtTokenPair> refresh(RefreshRequest refreshRequest) {
        var jwt = authService.refresh(refreshRequest.refreshToken());
        return ResponseEntity.ok(jwt);
    }

    @Override
    public ResponseEntity<Void> logout(String authorizationHeader) {
        authService.logout(authorizationHeader.substring("Bearer " .length()));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> logoutMe() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> register(RegistrationRequest request) {
        authService.register(request);
        return ResponseEntity.noContent().build();
    }
}
