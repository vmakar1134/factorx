package com.makar.tenant.security;

import com.makar.tenant.user.UserId;
import io.jsonwebtoken.Claims;
import lombok.Getter;

import java.time.Instant;

@Getter
public class JwtDetails {

    public static final String TENANT_NAME_CLAIM = "tenant";
    public static final String TOKEN_TYPE_CLAIM = "type";

    private final String tenantName;
    private final UserId userId;
    private final TokenType tokenType;
    private final Instant issuedAt;
    private final Instant expiredAt;

    private JwtDetails(Claims claims) {
        this.tenantName = claims.get(TENANT_NAME_CLAIM, String.class);
        this.tokenType = TokenType.valueOf(claims.get(TOKEN_TYPE_CLAIM, String.class));
        this.userId = UserId.fromString(claims.getSubject());
        this.issuedAt = claims.getIssuedAt().toInstant();
        this.expiredAt = claims.getExpiration().toInstant();
    }

    public static JwtDetails from(Claims claims) {
        return new JwtDetails(claims);
    }
}
