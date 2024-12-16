package com.makar.tenant.admin.rest.model;

public record RegisterAdminRequest(
        String email,
        String password
) {
}
