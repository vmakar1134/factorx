package com.makar.tenant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class PrincipalLookupResolver {

    private final List<PrincipalLookup> principalLookups;

    // TODO: change role to 'storage' or smth other that would indicate a table.
    Optional<UserPrincipal> resolvePrincipal(String username, RoleName roleName) throws UsernameNotFoundException {
        return principalLookups.stream()
                .filter(principalLookup -> roleName == principalLookup.supportedRole())
                .findFirst()
                .map(principalLookup -> principalLookup.findByUsername(username));
    }
}
