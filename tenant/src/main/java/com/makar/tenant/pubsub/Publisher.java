package com.makar.tenant.pubsub;

import org.springframework.data.redis.listener.ChannelTopic;

public interface Publisher {

    void publish(ChannelTopic topic, Message message);

}
