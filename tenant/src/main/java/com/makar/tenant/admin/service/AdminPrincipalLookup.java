package com.makar.tenant.admin.service;

import com.makar.tenant.admin.repository.AdminRepository;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.RoleName;
import com.makar.tenant.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminPrincipalLookup implements PrincipalLookup {

    private final AdminRepository adminRepository;

    @Override
    public Optional<UserPrincipal> findByUsername(String username) {
        return adminRepository.findByEmail(username)
                .map(admin -> new UserPrincipal()
                        .username(admin.email())
                        .password(admin.password())
                        .role(supportedRole()));
    }

    @Override
    public RoleName supportedRole() {
        return RoleName.ADMIN;
    }
}
