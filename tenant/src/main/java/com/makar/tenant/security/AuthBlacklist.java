package com.makar.tenant.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
class AuthBlacklist {

    private static final String PLACEHOLDER = "1";

    private final StringRedisTemplate redisTemplate;

    void blacklist(String jwt, Instant keepUntil) {
        if (!keepUntil.isAfter(Instant.now())) {
            throw new IllegalArgumentException("Token expiredAt must be in future");
        }

        var ttlSeconds = keepUntil.getEpochSecond() - Instant.now().getEpochSecond();
        redisTemplate.opsForValue().setIfAbsent(hashToken(jwt), PLACEHOLDER, ttlSeconds, TimeUnit.SECONDS);
    }

    boolean isBlacklisted(String jwt) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(hashToken(jwt)));
    }

    private String hashToken(String jwt) {
        return DigestUtils.sha256Hex(jwt);
    }

}
