package com.makar.tenant.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class PrincipalLookupResolver {

    private final Map<PrincipalLookupTable, PrincipalLookup> lookupByTable;

    public PrincipalLookupResolver(List<PrincipalLookup> lookupByTable) {
        this.lookupByTable = lookupByTable.stream()
                .collect(Collectors.toMap(PrincipalLookup::table, Function.identity()));
    }

    Optional<UserPrincipal> resolvePrincipal(String username, PrincipalLookupTable principalLookupTable) throws UsernameNotFoundException {
        if (!lookupByTable.containsKey(principalLookupTable)) {
            return Optional.empty();
        }

        return Optional.of(lookupByTable.get(principalLookupTable).findByUsername(username));
    }
}
