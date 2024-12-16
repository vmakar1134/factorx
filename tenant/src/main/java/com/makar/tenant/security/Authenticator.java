package com.makar.tenant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class Authenticator {

    private final JwtService jwtService;

    private final AuthBlacklist authBlacklist;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public String authenticate(UserPrincipal principal, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, password));
        return jwtService.generateToken(principal);
    }

    public void logout(String jwt) {
        authBlacklist.blacklist(jwt, jwtService.extractExpiredAt(jwt));
    }

    public <T> T register(Credentials credentials, Function<Credentials, T> registrationCallback) {
        var updatedCredentials = new Credentials(credentials.username(), passwordEncoder.encode(credentials.password()));
        return registrationCallback.apply(updatedCredentials);
    }

}
