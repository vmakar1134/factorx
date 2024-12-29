package com.makar.tenant.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makar.tenant.pubsub.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private static final String SUBSCRIBER_METHOD_NAME = "handle";

    private final ConfigurableListableBeanFactory beanFactory;

    @Bean
     RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setEnableTransactionSupport(true);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        template.setKeySerializer(RedisSerializer.string());
        return template;
    }

    @Bean
    List<MessageListenerAdapter> listenerAdapters(List<Subscriber> subscribers) {
        return subscribers.stream()
                .map(subscriber -> {
                    var adapter = buildListenerAdapter(subscriber);
                    var subscriberName = subscriber.getClass().getSimpleName().toLowerCase();
                    var beanName = subscriberName + adapter.getClass().getSimpleName().toLowerCase();
                    customBeanRegistration(adapter, beanName);
                    return adapter;
                })
                .toList();
    }

    @Bean
    @Primary
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                List<MessageListenerAdapter> listenerAdapters) {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        listenerAdapters.forEach(adapter -> container.addMessageListener(adapter, ((Subscriber) adapter.getDelegate()).topic()));
        return container;
    }

    private MessageListenerAdapter buildListenerAdapter(Subscriber subscriber) {
        var adapter = new MessageListenerAdapter(subscriber, SUBSCRIBER_METHOD_NAME);
        adapter.setSerializer(RedisSerializer.java());
        return adapter;
    }

    private void customBeanRegistration(MessageListenerAdapter adapter, String beanName) {
        beanFactory.initializeBean(adapter, beanName);
        beanFactory.autowireBean(adapter);
        beanFactory.registerSingleton(beanName, adapter);
    }

}
