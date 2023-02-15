package com.makar.factorx.manager.service;

import org.springframework.stereotype.Component;


@Component
public class TokenIdentifierResolver {

    public String resolveTenant() {
        // TODO: implement.
        return "manager";
    }
}
