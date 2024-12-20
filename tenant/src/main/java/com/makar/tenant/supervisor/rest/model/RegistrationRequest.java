package com.makar.tenant.supervisor.rest.model;

public record RegistrationRequest(
        String email,
        String password
) {
}
