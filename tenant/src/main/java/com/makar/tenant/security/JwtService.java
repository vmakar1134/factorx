package com.makar.tenant.security;

import com.makar.tenant.context.TenantNameContextHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
class JwtService {

    private static final String SECRET_STRING = "your-secret-key-256-bytes-long-your-secret-key-256-bytes-long";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
    private static final String ROLE_CLAIM = "role";
    private static final String TENANT_NAME_CLAIM = "tenant";

    String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    RoleName extractRole(String token) {
        var roleName = extractClaim(token, claims -> claims.get(ROLE_CLAIM, String.class));
        return RoleName.valueOf(roleName);
    }

    String extractTenantName(String token) {
        return extractClaim(token, claims -> claims.get(TENANT_NAME_CLAIM, String.class));
    }

    Instant extractExpiredAt(String token) {
        return extractClaim(token, Claims::getExpiration).toInstant();
    }

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    String generateToken(UserPrincipal userDetails) {
        var tenantName = TenantNameContextHolder.find().orElseThrow();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 5 )) // 5 minutes
                .addClaims(Map.of(ROLE_CLAIM, userDetails.getRole(), TENANT_NAME_CLAIM, tenantName))
                .signWith(SECRET_KEY)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
