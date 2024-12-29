package com.makar.tenant.redis;

import com.makar.tenant.util.Checker;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class RedisKeyTemplate {

    private static final String DELIMITER = ":";

    private final String root;

    private String path;

    public static RedisKeyTemplate root(String... keys) {
        validateNulls(keys);
        validateBlanks(keys);

        return new RedisKeyTemplate(String.join(DELIMITER, keys));
    }

    public RedisKeyTemplate append(Object... keys) {
        validateNulls(keys);
        var elements = Arrays.stream(keys)
                .map(Object::toString)
                .toArray(String[]::new);

        validateBlanks(elements);
        var path = (isBlank(this.path) ? root : this.path) + DELIMITER + String.join(DELIMITER, elements);
        return new RedisKeyTemplate(root, path);
    }

    public String key() {
        return isBlank(path) ? root : path;
    }

    private static void validateNulls(Object[] keys) {
        if (ArrayUtils.isEmpty(keys) || Checker.containsNull(keys)) {
            throw new IllegalArgumentException("Key segments cannot be empty or have null values");
        }
    }

    private static void validateBlanks(String[] keys) {
        if (ArrayUtils.isEmpty(keys) || Checker.containsBlanks(keys)) {
            throw new IllegalArgumentException("Key segments cannot be empty or have null values");
        }
    }

}
