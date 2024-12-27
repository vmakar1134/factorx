package com.makar.tenant.worker.service;

import com.makar.tenant.security.Credentials;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.UserPrincipal;
import com.makar.tenant.security.UserPrincipalLocator;
import com.makar.tenant.user.UserId;
import com.makar.tenant.worker.Worker;
import com.makar.tenant.worker.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static com.makar.tenant.user.UserLookupTable.WORKER;

@Service
@RequiredArgsConstructor
public class WorkerPrincipalLookup implements PrincipalLookup {

    private final WorkerRepository workerRepository;

    @Override
    public UserPrincipalLocator locate(Long id) {
        Supplier<UserPrincipal> principalSupplier = () -> workerRepository.findById(id)
                .map(this::buildPrincipal)
                .orElseThrow();

        return new UserPrincipalLocator(principalSupplier, WORKER);
    }

    @Override
    public UserPrincipalLocator locate(String username) {
        Supplier<UserPrincipal> principalSupplier = () -> workerRepository.findByUsername(username)
                .map(this::buildPrincipal)
                .orElseThrow();

        return new UserPrincipalLocator(principalSupplier, WORKER);
    }

    private UserPrincipal buildPrincipal(Worker worker) {
        return new UserPrincipal(new UserId(worker.id(), WORKER), Credentials.from(worker.username(), worker.password()));
    }

}
