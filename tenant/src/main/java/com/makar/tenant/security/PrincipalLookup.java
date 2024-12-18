package com.makar.tenant.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface PrincipalLookup {

    Optional<UserPrincipal> find(Long id);

    Optional<UserPrincipal> find(String username);

    PrincipalLookupTable lookupTable();

    default UserPrincipal get(Long id) {
        return find(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by id: " + id));
    }

    default UserPrincipal get(String username) {
        return find(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username: " + username));
    }

}
