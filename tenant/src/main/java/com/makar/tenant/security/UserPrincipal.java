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

    RoleName role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return credentials.password();
    }

    @Override
    public String getUsername() {
        return credentials.username();
    }

    public RoleName getRole() {
        return role;
    }

}
