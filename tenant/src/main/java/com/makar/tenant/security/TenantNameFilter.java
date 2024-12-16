package com.makar.tenant.security;

import com.makar.tenant.context.TenantNameContextHolder;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantNameFilter extends OncePerRequestFilter {

    private static final String TENANT_NAME_PARAM = "X-Tenant-Id";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final int BEARER_LENGTH = 7;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        var tenantName = extractTenantName(request);
        if (tenantName.isEmpty()) {
            setErrorResponse(response);
            return;
        }

        TenantNameContextHolder.set(tenantName.get());
        chain.doFilter(request, response);
    }

    private Optional<String> extractTenantName(HttpServletRequest request) {
        return extractJwt(request)
                .map(jwtService::extractTenantName)
                .or(() -> Optional.ofNullable(request.getHeader(TENANT_NAME_PARAM)))
                .or(() -> Optional.ofNullable(request.getParameter(TENANT_NAME_PARAM)));
    }

    private Optional<String> extractJwt(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .map(header -> header.substring(BEARER_LENGTH));
    }

    private void setErrorResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try {
            response.getWriter().write("Missing tenant header or tenant parameter: " + TENANT_NAME_PARAM);
        } catch (IOException e) {
            log.error("Error writing response", e);
        }
    }
}
