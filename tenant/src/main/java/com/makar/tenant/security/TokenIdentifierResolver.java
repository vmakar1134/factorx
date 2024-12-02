package com.makar.tenant.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Component
public class TokenIdentifierResolver {

    private static final String TENANT_NAME_PARAM = "X-Tenant-Id";

    public Optional<String> resolveTenant() {
        var securityContext = SecurityContextHolder.getContext();
        boolean isAuthenticated = Optional.ofNullable(securityContext.getAuthentication())
                .map(Authentication::isAuthenticated)
                .orElse(false);

        if (!isAuthenticated) {
            return getHeader(TENANT_NAME_PARAM)
                    .or(() -> getQueryParam(TENANT_NAME_PARAM));
        }

        var principal = (UserPrincipal) securityContext.getAuthentication().getPrincipal();
        return Optional.of(principal.getTenantName());
    }

    private static Optional<String> getHeader(String headerName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (nonNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            return Optional.ofNullable(request.getHeader(headerName));
        }
        return Optional.empty();
    }

    public Optional<String> getQueryParam(String paramName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (nonNull(attributes)) {
            return Optional.of(attributes.getRequest())
                    .map(request -> request.getParameter(paramName));
        }
        return Optional.empty();
    }
}
