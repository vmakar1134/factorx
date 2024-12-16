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
import static java.util.Objects.nonNull;
import static java.util.function.Predicate.not;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final AuthBlacklist authBlacklist;

    private final PrincipalLookupResolver principalLookupResolver;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws IOException, ServletException {
        extractJwt(request)
                .filter(not(authBlacklist::isBlacklisted))
                .flatMap(this::resolvePrincipal)
                .ifPresent(this::authenticate);

        chain.doFilter(request, response);
    }

    private Optional<UserPrincipal> resolvePrincipal(String jwt) {
        var username = jwtService.extractUsername(jwt);
        var table = jwtService.extractTable(jwt);
        if (isNotBlank(username) && nonNull(table)) {
            return principalLookupResolver.resolvePrincipal(username, table);
        }
        return Optional.empty();
    }

    private void authenticate(UserPrincipal userPrincipal) {
        var authentication = UsernamePasswordAuthenticationToken.authenticated(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Optional<String> extractJwt(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .map(header -> header.substring(BEARER_LENGTH));
    }

}
