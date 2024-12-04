package com.makar.tenant.admin.service;

import com.makar.tenant.admin.entity.Admin;
import com.makar.tenant.admin.repository.AdminRepository;
import com.makar.tenant.admin.rest.model.LoginAdminRequest;
import com.makar.tenant.admin.rest.model.RegisterAdminRequest;
import com.makar.tenant.security.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminRepository adminRepository;

    private final AdminPrincipalLookup adminPrincipalLookup;

    private final Authenticator authenticator;

    public void register(RegisterAdminRequest request) {
        var admin = new Admin(null, authenticator.encodePassword(request.password()), null, null, request.email());
        adminRepository.save(admin);
    }

    public String login(LoginAdminRequest request) {
        var principal = adminPrincipalLookup.findByUsername(request.email());
        return authenticator.authenticate(principal, request.password());
    }

}
