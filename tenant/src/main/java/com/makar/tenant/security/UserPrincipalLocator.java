package com.makar.tenant.security;

import com.makar.tenant.user.UserLookupTable;

import java.util.function.Supplier;

public record UserPrincipalLocator(
        Supplier<UserPrincipal> principal,
        UserLookupTable table
) {
}
