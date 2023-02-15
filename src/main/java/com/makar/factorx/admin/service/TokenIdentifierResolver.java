package com.makar.factorx.admin.service;

import org.springframework.stereotype.Component;


@Component
public class TokenIdentifierResolver {

    public String resolveTenant() {
        // TODO: implement.
        return "admin";
    }
}
