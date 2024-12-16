package com.makar.tenant.security;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Value
public class UserPrincipal implements UserDetails {

    Credentials credentials;

    PrincipalLookupTable table;

    /**
     * Currently we are returning a single authority based on the table where the {@code Principal} is stored.
     * <p>In the future we can add more authorities based on the roles and permissions of the user.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(table.toString()));
    }

    @Override
    public String getPassword() {
        return credentials.password();
    }

    @Override
    public String getUsername() {
        return credentials.username();
    }

    public PrincipalLookupTable getTable() {
        return table;
    }

}
