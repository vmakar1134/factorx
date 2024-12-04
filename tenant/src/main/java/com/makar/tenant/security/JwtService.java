package com.makar.tenant.security;

import com.makar.tenant.tenantcontext.TenantNameContextHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
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

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    String generateToken(UserPrincipal userDetails) {
        var tenantName = TenantNameContextHolder.find().orElseThrow();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .addClaims(Map.of(ROLE_CLAIM, userDetails.getRole(), TENANT_NAME_CLAIM, tenantName))
                .signWith(SECRET_KEY)
                .compact();
    }

    boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
