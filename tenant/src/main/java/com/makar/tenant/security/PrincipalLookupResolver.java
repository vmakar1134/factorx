package com.makar.tenant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalLookupResolver {

    private final List<PrincipalLookup> principalLookups;

    public UserPrincipal resolvePrincipal(String username, RoleName roleName) throws UsernameNotFoundException {
        for (PrincipalLookup principalLookup : principalLookups) {
            if (roleName == principalLookup.supportedRole()) {
                return principalLookup.findByUsername(username);
            }
        }

        throw new UsernameNotFoundException("Could not resolve principal: %s for role: %s".formatted(username, roleName));
    }
}
