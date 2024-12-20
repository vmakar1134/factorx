package com.makar.tenant.supervisor.rest.model;

public record LoginSupervisorRequest(
        String email,
        String password
) {
}
