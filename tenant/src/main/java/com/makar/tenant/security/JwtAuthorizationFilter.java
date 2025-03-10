package com.makar.tenant.security;

import java.io.IOException;
import java.util.Optional;

import static com.makar.tenant.security.JwtService.AUTHORIZATION_HEADER;
import static com.makar.tenant.security.JwtService.BEARER_LENGTH;

import io.jsonwebtoken.JwtException;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final LoginBlacklist loginBlacklist;

    private final PrincipalLookup principalLookup;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws IOException, ServletException {
        try {
            extractJwt(request)
                    .flatMap(jwt -> resolvePrincipal(jwt)
                            .filter(principal -> !isBlacklisted(principal, jwt)))
                    .ifPresent(this::authenticate);

            chain.doFilter(request, response);
        } catch (JwtException e) {
            log.error("Error in JwtAuthorizationFilter", e);
            handleException(response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"JWT token expired\"}");
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
        return principalLookup.locate(jwtDetails.userId());
    }

    private void authenticate(UserPrincipal userPrincipal) {
        var authentication = UsernamePasswordAuthenticationToken.authenticated(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
