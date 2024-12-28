package com.makar.tenant.redis;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RedisKeyTemplate {

    private static final String DELIMITER = ":";

    private final String root;

    private String path;

    public static RedisKeyTemplate root(String... keys) {
        if (keys.length == 0) {
            throw new IllegalArgumentException("Root key segment cannot be empty");
        }

        return new RedisKeyTemplate(String.join(DELIMITER, keys));
    }

    public String path(String... keys) {
        return keys.length > 0 ? root + DELIMITER + String.join(DELIMITER, keys) : root;
    }

    public String key() {
        return isBlank(path) ? root : path;
    }

}
