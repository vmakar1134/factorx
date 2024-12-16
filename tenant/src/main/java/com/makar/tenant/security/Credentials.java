package com.makar.tenant.security;

import java.io.Serializable;

public record Credentials(
        String username,
        String password
) implements Serializable {

    public static Credentials from(String username, String password) {
        return new Credentials(username, password);
    }

}
