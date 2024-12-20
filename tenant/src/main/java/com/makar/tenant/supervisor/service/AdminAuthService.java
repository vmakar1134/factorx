package com.makar.tenant.supervisor.service;

import com.makar.tenant.supervisor.Supervisor;
import com.makar.tenant.supervisor.AdminRepository;
import com.makar.tenant.security.AuthService;
import com.makar.tenant.security.Authenticator;
import com.makar.tenant.security.Credentials;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService extends AuthService {

    private final AdminRepository adminRepository;

    public AdminAuthService(Authenticator authenticator,
                            AdminRepository adminRepository,
                            AdminPrincipalLookup adminPrincipalLookup) {
        super(authenticator, adminPrincipalLookup);
        this.adminRepository = adminRepository;
    }

    @Override
    protected void saveEntity(Credentials credentials) {
        var admin = new Supervisor(null, credentials.username(), null, null, credentials.password());
        adminRepository.save(admin);
    }

}
