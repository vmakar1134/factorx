package com.makar.tenant.security;

public record JwtTokenPair(
        String accessToken,
        String refreshToken
) {
}
