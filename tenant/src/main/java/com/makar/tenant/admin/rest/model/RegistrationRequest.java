package com.makar.tenant.admin.rest.model;

public record RegistrationRequest(
        String email,
        String password
) {
}
