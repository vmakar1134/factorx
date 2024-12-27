package com.makar.tenant.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.makar.tenant.security.JwtService.AUTHORIZATION_HEADER;
import static com.makar.tenant.security.JwtService.BEARER_LENGTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final LoginBlacklist loginBlacklist;

    private final PrincipalLookupResolver principalLookupResolver;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws IOException, ServletException {
        extractJwt(request)
                .flatMap(jwt -> resolvePrincipal(jwt)
                        .filter(principal -> !isBlacklisted(principal, jwt)))
                .ifPresent(this::authenticate);

        chain.doFilter(request, response);
    }

    private Optional<String> extractJwt(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .map(header -> header.substring(BEARER_LENGTH));
    }

    private boolean isBlacklisted(UserPrincipal principal, String jwt) {
        if (loginBlacklist.isPresent(jwt)) {
            return true;
        }
        if (!loginBlacklist.isPresent(principal)) {
            return false;
        }

        var blacklistAt = loginBlacklist.get(principal);
        return jwtService.parseAccessJwt(jwt).issuedAt().isBefore(blacklistAt);
    }

    private Optional<UserPrincipal> resolvePrincipal(String jwt) {
        var jwtDetails = jwtService.parseAccessJwt(jwt);
        return principalLookupResolver.resolvePrincipal(jwtDetails.userId());
    }

    private void authenticate(UserPrincipal userPrincipal) {
        var authentication = UsernamePasswordAuthenticationToken.authenticated(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
