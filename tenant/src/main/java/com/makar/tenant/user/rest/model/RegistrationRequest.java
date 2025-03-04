package com.makar.tenant.user.rest.model;

import com.makar.tenant.security.UserRole;

public record RegistrationRequest(
        String email,
        String password,
        UserRole role
) {
}
