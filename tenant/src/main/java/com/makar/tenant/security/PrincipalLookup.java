package com.makar.tenant.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface PrincipalLookup {

    Optional<Credentials> findCredentials(String username);

    RoleName supportedRole();

    default UserPrincipal findByUsername(String username) throws UsernameNotFoundException {
        var credentials = findCredentials(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));

        return new UserPrincipal(credentials, supportedRole());
    }
}
