package com.makar.tenant.admin.service;

import com.makar.tenant.admin.entity.Admin;
import com.makar.tenant.admin.repository.AdminRepository;
import com.makar.tenant.admin.rest.model.LoginAdminRequest;
import com.makar.tenant.admin.rest.model.RegisterAdminRequest;
import com.makar.tenant.security.RoleName;
import com.makar.tenant.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public void register(RegisterAdminRequest request) {
        var admin = new Admin(null, passwordEncoder.encode(request.password()), null, null, request.email());
        adminRepository.save(admin);
    }

    public void login(LoginAdminRequest request) {
        var principal = new UserPrincipal()
                .username(request.email())
                .password(request.password())
                .role(RoleName.ADMIN);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, request.password()));
    }

}
