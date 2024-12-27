package com.makar.tenant.user.supervisor.rest.model;

public record RegistrationRequest(
        String email,
        String password
) {
}
