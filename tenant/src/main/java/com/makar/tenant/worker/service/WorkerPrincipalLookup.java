package com.makar.tenant.worker.service;

import com.makar.tenant.security.Credentials;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.PrincipalLookupTable;
import com.makar.tenant.security.UserPrincipal;
import com.makar.tenant.worker.Worker;
import com.makar.tenant.worker.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerPrincipalLookup implements PrincipalLookup {

    private final WorkerRepository workerRepository;

    @Override
    public Optional<UserPrincipal> find(Long id) {
        return workerRepository.findById(id)
                .map(this::buildPrincipal);
    }

    @Override
    public Optional<UserPrincipal> find(String username) {
        return workerRepository.findByUsername(username)
                .map(this::buildPrincipal);
    }

    private UserPrincipal buildPrincipal(Worker worker) {
        return new UserPrincipal(worker.id(), Credentials.from(worker.username(), worker.password()), lookupTable());
    }

    @Override
    public PrincipalLookupTable lookupTable() {
        return PrincipalLookupTable.USER;
    }
}
