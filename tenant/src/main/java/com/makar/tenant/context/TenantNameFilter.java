package com.makar.tenant.context;

import com.makar.tenant.security.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.makar.tenant.security.JwtService.AUTHORIZATION_HEADER;
import static com.makar.tenant.security.JwtService.BEARER_LENGTH;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantNameFilter extends OncePerRequestFilter {

    private static final String TENANT_NAME_PARAM = "X-Tenant-Id";

    private static final List<String> EXCLUDED_METHODS = List.of(HttpMethod.OPTIONS.name());

    private static final List<String> EXCLUDED_URLS = List.of("/actuator/**",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/swagger-ui");

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        try {
            if (isExcluded(request)) {
                chain.doFilter(request, response);
                return;
            }

            var tenantName = extractTenantName(request);
            if (tenantName.isEmpty()) {
                setErrorResponse(response);
                return;
            }

            TenantNameContextHolder.set(tenantName.get());
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error in TenantNameFilter", e);
            handleException(response);
        }
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"JWT token expired\"}");
    }

    private boolean isExcluded(HttpServletRequest request) {
        AntPathMatcher matcher = new AntPathMatcher();
        return EXCLUDED_METHODS.contains(request.getMethod()) || EXCLUDED_URLS.stream()
                .anyMatch(url -> matcher.match(url, request.getRequestURI()));
    }


    private Optional<String> extractTenantName(HttpServletRequest request) {
        return extractJwt(request)
                .map(jwt -> jwtService.parseAccessJwt(jwt).tenantName())
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
