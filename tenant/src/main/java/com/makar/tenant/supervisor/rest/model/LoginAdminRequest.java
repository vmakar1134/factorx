package com.makar.tenant.supervisor.rest.model;

public record LoginAdminRequest(
        String email,
        String password
) {
}
