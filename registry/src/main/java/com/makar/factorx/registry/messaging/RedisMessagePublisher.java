package com.makar.factorx.registry.messaging;

import static com.makar.factorx.registry.messaging.Topic.CREATE_TENANT_ADMIN;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String message) {
        redisTemplate.convertAndSend(CREATE_TENANT_ADMIN.getValue(), message);
    }

}
