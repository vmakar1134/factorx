package com.makar.tenant.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static com.makar.tenant.security.JwtDetails.ROLE_CLAIM;
import static com.makar.tenant.security.JwtDetails.TENANT_NAME_CLAIM;
import static com.makar.tenant.security.JwtDetails.TOKEN_TYPE_CLAIM;
import static com.makar.tenant.security.TokenType.ACCESS;
import static com.makar.tenant.security.TokenType.REFRESH;

import com.makar.tenant.context.TenantNameContextHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final int BEARER_LENGTH = 7;
    private static final String ACCESS_TOKEN_SECRET_STRING = "access-secret-key-256-bytes-long-your-secret-key-256-bytes-long";
    private static final String REFRESH_TOKEN_SECRET_STRING = "refresh-secret-key-256-bytes-long-your-secret-key-256-bytes-long";
    private static final Key ACCESS_TOKEN_SECRET_KEY = Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET_STRING.getBytes());
    private static final Key REFRESH_TOKEN_SECRET_KEY = Keys.hmacShaKeyFor(REFRESH_TOKEN_SECRET_STRING.getBytes());
    private static final int ACCESS_TOKEN_EXPIRATION_MILIS = 1000 * 60 * 60; // 1 hour
    private static final int REFRESH_TOKEN_EXPIRATION_MILIS = 1000 * 60 * 60 * 24; // 24 hours

    public JwtDetails parseAccessJwt(String jwt) {
        return JwtDetails.from(parseClaims(jwt, ACCESS_TOKEN_SECRET_KEY));
    }

    JwtTokenPair generateTokens(UserPrincipal userPrincipal) {
        var tenantName = TenantNameContextHolder.find().orElseThrow(() -> new IllegalStateException("Tenant name is not set"));
        final String role = userPrincipal.getRole().toString();
        var accessClaims = customClaims(ACCESS, tenantName, role);
        var refreshClaims = customClaims(REFRESH, tenantName, role);
        var userId = userPrincipal.getUserId();
        return new JwtTokenPair(
                generateJwt(userId, ACCESS_TOKEN_EXPIRATION_MILIS, ACCESS_TOKEN_SECRET_KEY, accessClaims),
                generateJwt(userId, REFRESH_TOKEN_EXPIRATION_MILIS, REFRESH_TOKEN_SECRET_KEY, refreshClaims));
    }

    JwtTokenPair refresh(String refreshToken) {
        var refreshJwtDetails = JwtDetails.from(parseClaims(refreshToken, REFRESH_TOKEN_SECRET_KEY));
        var userId = refreshJwtDetails.userId();
        var tenantName = refreshJwtDetails.tenantName();
        final String role = refreshJwtDetails.role();

        Map<String, Object> accessTokenClaims = customClaims(REFRESH, tenantName, role);
        Map<String, Object> refreshTokenClaims = customClaims(ACCESS, tenantName, role);
        var accessJwt = generateJwt(userId, ACCESS_TOKEN_EXPIRATION_MILIS, ACCESS_TOKEN_SECRET_KEY, accessTokenClaims);
        var refreshJwt = generateJwt(userId, REFRESH_TOKEN_EXPIRATION_MILIS, REFRESH_TOKEN_SECRET_KEY, refreshTokenClaims);
        return new JwtTokenPair(accessJwt, refreshJwt);
    }

    private Map<String, Object> customClaims(TokenType tokenType, String tenantName, String role) {
        return Map.of(TOKEN_TYPE_CLAIM, tokenType, TENANT_NAME_CLAIM, tenantName, ROLE_CLAIM, role);
    }

    private String generateJwt(Long userId, int expirationMillis, Key secretKey, Map<String, Object> customClaims) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .addClaims(customClaims)
                .signWith(secretKey)
                .compact();
    }

    private Claims parseClaims(String jwt, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

}
