package com.makar.tenant.user.rest.model;

public record LoginRequest(
        String email,
        String password
) {
}
