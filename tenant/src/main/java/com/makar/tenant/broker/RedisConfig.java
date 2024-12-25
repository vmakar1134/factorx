package com.makar.tenant.broker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    private static final String SUBSCRIBER_METHOD_NAME = "handle";

    @Bean
    RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    MessageListenerAdapter taskCreatedListenerAdapter(TaskCreatedSubscriber taskCreatedSubscriber) {
        var adapter = new MessageListenerAdapter(taskCreatedSubscriber, SUBSCRIBER_METHOD_NAME);
        adapter.setSerializer(RedisSerializer.java());
        return adapter;
    }

    @Bean
    MessageListenerAdapter taskUpdatedListenerAdapter(TaskUpdatedSubscriber taskUpdatedSubscriber) {
        var adapter = new MessageListenerAdapter(taskUpdatedSubscriber, SUBSCRIBER_METHOD_NAME);
        adapter.setSerializer(RedisSerializer.java());
        return adapter;
    }

    @Bean
    @Primary
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                MessageListenerAdapter taskCreatedListenerAdapter,
                                                                MessageListenerAdapter taskUpdatedListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(taskCreatedListenerAdapter, Topics.TASK_CREATED);
        container.addMessageListener(taskUpdatedListenerAdapter, Topics.TASK_UPDATED);
        return container;
    }

}
