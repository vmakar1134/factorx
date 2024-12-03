package com.makar.tenant.user.service;

import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.RoleName;
import com.makar.tenant.security.UserPrincipal;
import com.makar.tenant.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPrincipalLookup implements PrincipalLookup {

    private final UserRepository userRepository;

    @Override
    public Optional<UserPrincipal> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserPrincipal()
                        .username(user.username())
                        .password(user.password())
                        .role(supportedRole()));
    }

    @Override
    public RoleName supportedRole() {
        return RoleName.USER;
    }
}
