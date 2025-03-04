package com.makar.tenant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Authenticator {

    private final JwtService jwtService;

    private final LoginBlacklist loginBlacklist;

   private final AuthenticationManager authenticationManager;

    public JwtTokenPair authenticate(UserPrincipal principal, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, password));
        return jwtService.generateTokens(principal);
    }

    public JwtTokenPair refresh(String refreshToken) {
        return jwtService.refresh(refreshToken);
    }

    public void logout(String jwt) {
        loginBlacklist.add(jwt, jwtService.parseAccessJwt(jwt).expiredAt());
    }

    public void logout(UserPrincipal principal) {
        loginBlacklist.add(principal);
    }
}
