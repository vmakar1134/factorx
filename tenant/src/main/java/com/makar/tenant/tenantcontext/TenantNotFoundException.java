package com.makar.tenant.tenantcontext;

public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException() {
        super("Tenant not found in request");
    }

}
