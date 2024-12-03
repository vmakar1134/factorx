package com.makar.tenant.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface PrincipalLookup {

    Optional<UserPrincipal> findByUsername(String username);

    RoleName supportedRole();

    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));
    }
}
