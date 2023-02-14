package com.makar.factorx.tenant.service;

import org.springframework.stereotype.Component;


@Component
public class TokenIdentifierResolver {

    public String resolveTenant() {
        // TODO: implement.
        return "admin";
    }
}
