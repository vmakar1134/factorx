package com.makar.tenant.admin.service;

import com.makar.tenant.admin.entity.Admin;
import com.makar.tenant.admin.repository.AdminRepository;
import com.makar.tenant.admin.rest.model.LoginAdminRequest;
import com.makar.tenant.security.Authenticator;
import com.makar.tenant.security.Credentials;
import com.makar.tenant.admin.rest.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminRepository adminRepository;

    private final AdminPrincipalLookup adminPrincipalLookup;

    private final Authenticator authenticator;

    public void register(RegistrationRequest registrationRequest) {
        var credentials = Credentials.from(registrationRequest.email(), registrationRequest.password());
        var admin = authenticator.register(credentials, this::buildAdmin);
        adminRepository.save(admin);
    }

    private Admin buildAdmin(Credentials credentials) {
        return new Admin(null, credentials.username(), null, null, credentials.password());
    }

    public String login(LoginAdminRequest request) {
        var principal = adminPrincipalLookup.findByUsername(request.email());
        return authenticator.authenticate(principal, request.password());
    }

    public void logout(String jwt) {
        authenticator.logout(jwt);
    }
}
