package com.makar.tenant.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;

import static com.makar.tenant.security.RoleName.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    private static final String TENANT_NAME = "tenant123";
    private static final String X_TENANT_ID = "X-Tenant-Id";

    @Mock
    private JwtService jwtService;

    @Mock
    private PrincipalLookupResolver principalLookupResolver;

    @Mock
    private FilterChain filterChain;

    @Mock
    private RequestAttributes requestAttributes;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        RequestContextHolder.setRequestAttributes(requestAttributes);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldUseTenantClaim() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer some-jwt");
        when(jwtService.extractTenantName("some-jwt")).thenReturn(TENANT_NAME);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(requestAttributes).setAttribute(X_TENANT_ID, TENANT_NAME, RequestAttributes.SCOPE_REQUEST);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldUseTenantParam() throws ServletException, IOException {
        request.addParameter(X_TENANT_ID, TENANT_NAME);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(requestAttributes).setAttribute(X_TENANT_ID, TENANT_NAME, RequestAttributes.SCOPE_REQUEST);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldUseTenantHeader() throws ServletException, IOException {
        request.addHeader(X_TENANT_ID, TENANT_NAME);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(requestAttributes).setAttribute(X_TENANT_ID, TENANT_NAME, RequestAttributes.SCOPE_REQUEST);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldSendErrorResponseWhenNoTenant() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer some-jwt");
        when(jwtService.extractTenantName("some-jwt")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        assertTrue(response.getContentAsString().contains("Missing tenant header or tenant parameter"));
        verifyNoInteractions(filterChain);
    }

    @Test
    void doFilterInternal_shouldAuthenticateUserWhenJwtIsValid() throws ServletException, IOException {
        request.addHeader(X_TENANT_ID, TENANT_NAME);
        request.addHeader("Authorization", "Bearer some-jwt");
        var userDetails = mock(UserPrincipal.class);
        when(jwtService.extractUsername("some-jwt")).thenReturn("user");
        when(jwtService.extractRole("some-jwt")).thenReturn(USER);
        when(principalLookupResolver.resolvePrincipal("user", USER)).thenReturn(userDetails);
        when(jwtService.isTokenValid("some-jwt", userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertInstanceOf(UsernamePasswordAuthenticationToken.class, authentication);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldNotAuthenticateUserWhenJwtIsInvalid() throws ServletException, IOException {
        request.addHeader(X_TENANT_ID, TENANT_NAME);

        var roleName = RoleName.values()[0];
        request.addHeader("Authorization", "Bearer some-jwt");
        when(jwtService.extractUsername("some-jwt")).thenReturn("user");
        when(jwtService.extractRole("some-jwt")).thenReturn(roleName);
        var userDetails = mock(UserPrincipal.class);
        when(principalLookupResolver.resolvePrincipal("user", roleName)).thenReturn(userDetails);
        when(jwtService.isTokenValid("some-jwt", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldPassWhenNoAuthorizationHeader() throws ServletException, IOException {
        request.addHeader(X_TENANT_ID, TENANT_NAME);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
