package com.makar.tenant.pubsub;

import org.springframework.data.redis.listener.ChannelTopic;

public interface Subscriber {

    void handle(Message message);

    ChannelTopic topic();

}
