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

    private final LoginBlacklist loginBlacklist;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public String authenticate(UserPrincipal principal, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, password));
        return jwtService.generateToken(principal);
    }

    public void logout(String jwt) {
        loginBlacklist.add(jwt, jwtService.extractExpiredAt(jwt));
    }

    public void logout(UserPrincipal principal) {
        loginBlacklist.add(principal);
    }

    public <T> T register(Credentials credentials, Function<Credentials, T> registrationCallback) {
        var updatedCredentials = new Credentials(credentials.username(), passwordEncoder.encode(credentials.password()));
        return registrationCallback.apply(updatedCredentials);
    }

}
