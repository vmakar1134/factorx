package com.makar.tenant.security;

import java.io.Serializable;

public record Credentials(
        String username,
        String password
) implements Serializable {
}
