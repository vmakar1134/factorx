package com.makar.tenant.admin.rest.model;

public record LoginAdminRequest(
        String email,
        String password
) {
}
