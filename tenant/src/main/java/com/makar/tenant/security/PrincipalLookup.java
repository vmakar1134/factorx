package com.makar.tenant.security;

public interface PrincipalLookup {

    UserPrincipalLocator locate(Long id);

    UserPrincipalLocator locate(String username);

}
