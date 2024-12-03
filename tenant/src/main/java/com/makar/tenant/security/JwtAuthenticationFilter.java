package com.makar.tenant.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TENANT_NAME_PARAM = "X-Tenant-Id";

    private final JwtService jwtService;

    private final PrincipalLookupResolver principalLookupResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws IOException, ServletException {
        var authHeader = request.getHeader("Authorization");
        if (!isAuthorizationHeader(authHeader) && !hasTenantHeader(request) && !hasTenantRequestParam(request)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing tenant header or tenant parameter");
            return;
        }

        var jwt = authHeader.substring(7);
        var username = jwtService.extractUsername(jwt);
        var role = jwtService.extractRole(jwt);
        if (isNotBlank(username) && nonNull(role)) {
            var userDetails = principalLookupResolver.resolvePrincipal(username, role);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                var authToken = UsernamePasswordAuthenticationToken
                        .authenticated(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

    private boolean hasTenantRequestParam(HttpServletRequest request) {
        return isNotBlank(request.getParameter(TENANT_NAME_PARAM));
    }

    private boolean hasTenantHeader(HttpServletRequest request) {
        return isNotBlank(request.getHeader(TENANT_NAME_PARAM));
    }

    private boolean isAuthorizationHeader(String authHeader) {
        return isNotBlank(authHeader) && authHeader.startsWith("Bearer ");
    }

}
