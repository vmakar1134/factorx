package com.makar.tenant.user.supervisor.rest.model;

public record LoginSupervisorRequest(
        String email,
        String password
) {
}
