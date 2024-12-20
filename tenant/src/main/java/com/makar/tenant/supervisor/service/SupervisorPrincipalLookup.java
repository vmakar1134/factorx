package com.makar.tenant.supervisor.service;

import com.makar.tenant.supervisor.Supervisor;
import com.makar.tenant.supervisor.SupervisorRepository;
import com.makar.tenant.security.Credentials;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.PrincipalLookupTable;
import com.makar.tenant.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupervisorPrincipalLookup implements PrincipalLookup {

    private final SupervisorRepository supervisorRepository;

    @Override
    public Optional<UserPrincipal> find(Long id) {
        return supervisorRepository.findById(id)
                .map(this::buildPrincipal);
    }

    @Override
    public Optional<UserPrincipal> find(String username) {
        return supervisorRepository.findByEmail(username)
                .map(this::buildPrincipal);
    }

    private UserPrincipal buildPrincipal(Supervisor supervisor) {
        return new UserPrincipal(supervisor.id(), Credentials.from(supervisor.email(), supervisor.password()), lookupTable());
    }

    @Override
    public PrincipalLookupTable lookupTable() {
        return PrincipalLookupTable.ADMIN;
    }
}
