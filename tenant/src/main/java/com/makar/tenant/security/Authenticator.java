package com.makar.tenant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Authenticator {

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public String authenticate(UserPrincipal principal, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, password));
        return jwtService.generateToken(principal);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
