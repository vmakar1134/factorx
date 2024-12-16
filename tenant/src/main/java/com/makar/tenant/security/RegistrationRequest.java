package com.makar.tenant.security;

public record RegistrationRequest(
        String email,
        String password
) {
}
