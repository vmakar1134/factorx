package com.makar.tenant.config;

import static com.makar.tenant.messaging.Topic.CREATE_TENANT_ADMIN;

import com.makar.tenant.messaging.TenantAdminMessageSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@RequiredArgsConstructor
public class ListenerConfiguration {

    private final TenantAdminMessageSubscriber tenantAdminMessageSubscriber;

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(tenantAdminMessageSubscriber);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(messageListener(), ChannelTopic.of(CREATE_TENANT_ADMIN.getValue()));
        container.setConnectionFactory(connectionFactory);
        return container;
    }

}
