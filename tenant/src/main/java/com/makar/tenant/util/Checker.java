package com.makar.tenant.util;

import static org.apache.commons.lang3.StringUtils.isBlank;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Checker {

    public static boolean containsNull(Object... objects) {
        if (objects == null) {
            return true;
        }

        for (var object : objects) {
            if (object == null) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsNull(Iterable<?> iterable) {
        for (var i : iterable) {
            if (i == null) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsBlanks(String... strings) {
        if (strings == null) {
            return true;
        }

        for (var string : strings) {
            if (isBlank(string)) {
                return true;
            }
        }

        return false;
    }

}
