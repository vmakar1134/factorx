package com.makar.tenant.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
class LoginBlacklist {

    private static final String PLACEHOLDER = "1";

    private final RedisTemplate<String, String> redisTemplate;

    void add(String jwt, Instant keepUntil) {
        if (!keepUntil.isAfter(Instant.now())) {
            throw new IllegalArgumentException("Token expiredAt must be in future");
        }

        var ttlSeconds = keepUntil.getEpochSecond() - Instant.now().getEpochSecond();
        redisTemplate.opsForValue().setIfAbsent(hashToken(jwt), PLACEHOLDER, ttlSeconds, TimeUnit.SECONDS);
    }

    boolean isPresent(String jwt) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(hashToken(jwt)));
    }

    void add(UserPrincipal principal) {
        redisTemplate.opsForValue().setIfAbsent(idKey(principal), Instant.now().toString(), 1, TimeUnit.DAYS);
    }

    Instant get(UserPrincipal principal) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(idKey(principal)))
                .map(Instant::parse)
                .orElseThrow(() -> new IllegalArgumentException("Principal not found"));
    }

    boolean isPresent(UserPrincipal principal) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(idKey(principal)));
    }

    private String idKey(UserPrincipal principal) {
        return principal.getUserId().asString();
    }

    private String hashToken(String jwt) {
        return DigestUtils.sha256Hex(jwt);
    }

}
