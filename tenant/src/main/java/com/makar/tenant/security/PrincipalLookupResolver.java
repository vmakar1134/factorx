package com.makar.tenant.security;

import com.makar.tenant.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class PrincipalLookupResolver {

    private final List<PrincipalLookup> principalLookups;

    Optional<UserPrincipal> resolvePrincipal(UserId userId) {
        var id = userId.id();
        return findLookup(userId)
                .map(lookup -> lookup.locate(id).principal().get());
    }

    private Optional<PrincipalLookup> findLookup(UserId userId) {
        return principalLookups.stream()
                .filter(principalLookup -> principalLookup.locate(userId.id()).table().equals(userId.table()))
                .findFirst();
    }

}
