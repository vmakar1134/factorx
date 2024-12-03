package com.makar.tenant.security;

import org.springframework.security.core.AuthenticationException;

public class UserPrincipalAuthenticationException extends AuthenticationException {

    public UserPrincipalAuthenticationException(String msg) {
        super(msg);
    }

}
