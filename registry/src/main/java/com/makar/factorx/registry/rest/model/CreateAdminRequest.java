package com.makar.factorx.registry.rest.model;

public record CreateAdminRequest(
        String email,
        String otp
) {
}
