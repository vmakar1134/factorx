package com.makar.tenant.redis;

import com.makar.tenant.pubsub.Message;
import com.makar.tenant.pubsub.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher implements Publisher {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publish(ChannelTopic topic, Message message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
