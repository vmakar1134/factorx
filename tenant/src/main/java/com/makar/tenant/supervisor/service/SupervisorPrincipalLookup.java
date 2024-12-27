package com.makar.tenant.supervisor.service;

import com.makar.tenant.security.Credentials;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.UserPrincipal;
import com.makar.tenant.security.UserPrincipalLocator;
import com.makar.tenant.supervisor.Supervisor;
import com.makar.tenant.supervisor.SupervisorRepository;
import com.makar.tenant.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static com.makar.tenant.user.UserLookupTable.SUPERVISOR;

@Service
@RequiredArgsConstructor
public class SupervisorPrincipalLookup implements PrincipalLookup {

    private final SupervisorRepository supervisorRepository;

    @Override
    public UserPrincipalLocator locate(Long id) {
        Supplier<UserPrincipal> principalSupplier = () -> supervisorRepository.findById(id)
                .map(this::buildPrincipal)
                .orElseThrow();

        return new UserPrincipalLocator(principalSupplier, SUPERVISOR);
    }

    @Override
    public UserPrincipalLocator locate(String username) {
        Supplier<UserPrincipal> principalSupplier = () -> supervisorRepository.findByEmail(username)
                .map(this::buildPrincipal)
                .orElseThrow();

        return new UserPrincipalLocator(principalSupplier, SUPERVISOR);
    }

    private UserPrincipal buildPrincipal(Supervisor supervisor) {
        return new UserPrincipal(new UserId(supervisor.id(), SUPERVISOR), Credentials.from(supervisor.email(), supervisor.password()));
    }

}
