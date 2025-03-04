package com.makar.tenant.security;

import java.util.Optional;

public interface PrincipalLookup {

    Optional<UserPrincipal> locate(Long id);

    Optional<UserPrincipal> locate(String username);

}
