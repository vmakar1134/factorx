package com.makar.tenant.tenantcontext;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TenantNameContextHolder {

    private static final String TENANT_NAME_ATTRIBUTE = "X-Tenant-Id";

    public static void set(String tenantName) {
        if (isBlank(tenantName)) {
            log.error("Tenant name is blank");
            throw new IllegalArgumentException("Tenant name is blank");
        }

        var attributes = RequestContextHolder.getRequestAttributes();
        if (isNull(attributes)) {
            log.warn("Request attributes is null and cannot be set");
            throw new IllegalStateException("Request attributes is null and cannot be set");
        }

        attributes.setAttribute(TENANT_NAME_ATTRIBUTE, tenantName, SCOPE_REQUEST);
    }

    public static Optional<String> find() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(att -> att.getAttribute(TENANT_NAME_ATTRIBUTE, SCOPE_REQUEST))
                .map(String.class::cast);
    }
}
