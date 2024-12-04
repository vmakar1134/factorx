package com.makar.tenant.security;

import com.makar.tenant.tenantcontext.TenantNameContextHolder;
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

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TENANT_NAME_PARAM = "X-Tenant-Id";

    private final JwtService jwtService;

    private final PrincipalLookupResolver principalLookupResolver;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws IOException, ServletException {
        var tenantName = extractTenantName(request);
        if (tenantName.isEmpty()) {
            sendErrorResponse(response);
            return;
        }

        TenantNameContextHolder.set(tenantName.get());
        extractJwt(request).ifPresent(this::authenticate);
        chain.doFilter(request, response);
    }

    private void authenticate(String jwt) {
        var username = jwtService.extractUsername(jwt);
        var role = jwtService.extractRole(jwt);
        if (isNotBlank(username) && nonNull(role)) {
            var userDetails = principalLookupResolver.resolvePrincipal(username, role);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                var authentication = UsernamePasswordAuthenticationToken
                        .authenticated(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    private Optional<String> extractJwt(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .map(header -> header.substring(7));
    }

    private Optional<String> extractTenantName(HttpServletRequest request) {
        return extractJwt(request)
                .map(jwtService::extractTenantName)
                .or(() -> Optional.ofNullable(request.getHeader(TENANT_NAME_PARAM)))
                .or(() -> Optional.ofNullable(request.getParameter(TENANT_NAME_PARAM)));
    }

    private void sendErrorResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try {
            response.getWriter().write("Missing tenant header or tenant parameter");
        } catch (IOException e) {
            log.error("Error writing response", e);
        }
    }

}
