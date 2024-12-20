package com.makar.tenant.admin.service;

import com.makar.tenant.admin.entity.Admin;
import com.makar.tenant.admin.repository.AdminRepository;
import com.makar.tenant.security.Credentials;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.PrincipalLookupTable;
import com.makar.tenant.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminPrincipalLookup implements PrincipalLookup {

    private final AdminRepository adminRepository;

    @Override
    public Optional<UserPrincipal> find(Long id) {
        return adminRepository.findById(id)
                .map(this::buildPrincipal);
    }

    @Override
    public Optional<UserPrincipal> find(String username) {
        return adminRepository.findByEmail(username)
                .map(this::buildPrincipal);
    }

    private UserPrincipal buildPrincipal(Admin admin) {
        return new UserPrincipal(admin.id(), Credentials.from(admin.email(), admin.password()), lookupTable());
    }

    @Override
    public PrincipalLookupTable lookupTable() {
        return PrincipalLookupTable.ADMIN;
    }
}
