package com.makar.tenant.redis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RedisKeyTemplateTest {

    static Stream<Object[]> provideRootKeys() {
        return Stream.of(
                new Object[]{"task", "task"},
                new Object[]{"user", "user"},
                new Object[]{"order:123", "order:123"}
        );
    }

    @ParameterizedTest
    @MethodSource("provideRootKeys")
    void shouldCreateRootKey(String input, String expected) {
        var template = RedisKeyTemplate.root(input);
        assertEquals(expected, template.key());
    }

    static Stream<Object[]> provideAppendSegments() {
        return Stream.of(
                new Object[]{"task", "1", "comments", "task:1:comments"},
                new Object[]{"user", "profile", "settings", "user:profile:settings"},
                new Object[]{"order", "456", "details", "order:456:details"}
        );
    }

    @ParameterizedTest
    @MethodSource("provideAppendSegments")
    void shouldAppendSegments(String root, String append1, String append2, String expected) {
        var template = RedisKeyTemplate.root(root)
                .append(append1, append2);
        assertEquals(expected, template.key());
    }

    static Stream<Object[]> provideWildcardSegments() {
        return Stream.of(
                new Object[]{"task", "1", "comments", "task:1:comments"},
                new Object[]{"user", "*", "profile", "user:*:profile"},
                new Object[]{"event", "2024", "*", "event:2024:*"}
        );
    }

    @ParameterizedTest
    @MethodSource("provideWildcardSegments")
    void shouldHandleWildcardAppend(String root, String append1, String append2, String expected) {
        var template = RedisKeyTemplate.root(root)
                .append(append1, append2);
        assertEquals(expected, template.key());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowExceptionForEmptyRoot(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> RedisKeyTemplate.root(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowExceptionForEmptyAppend(String input) {
        var template = RedisKeyTemplate.root("task");
        assertThrows(IllegalArgumentException.class,
                () -> template.append(input));
    }

    @Test
    void shouldThrowExceptionForNullSegments() {
        var template = RedisKeyTemplate.root("task");
        assertThrows(IllegalArgumentException.class,
                () -> template.append(1, null));
    }
}
