package com.makar.tenant.supervisor.service;

import com.makar.tenant.supervisor.Supervisor;
import com.makar.tenant.supervisor.SupervisorRepository;
import com.makar.tenant.security.AuthService;
import com.makar.tenant.security.Authenticator;
import com.makar.tenant.security.Credentials;
import org.springframework.stereotype.Service;

@Service
public class SupervisorAuthService extends AuthService {

    private final SupervisorRepository supervisorRepository;

    public SupervisorAuthService(Authenticator authenticator,
                            SupervisorRepository supervisorRepository,
                            SupervisorPrincipalLookup supervisorPrincipalLookup) {
        super(authenticator, supervisorPrincipalLookup);
        this.supervisorRepository = supervisorRepository;
    }

    @Override
    protected void saveEntity(Credentials credentials) {
        var admin = new Supervisor(null, credentials.username(), null, null, credentials.password());
        supervisorRepository.save(admin);
    }

}
