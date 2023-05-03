package com.makar.factorx.registry.messaging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topic {

    CREATE_TENANT_ADMIN("tenant.admin.create");

    private final String value;

}
