package com.makar.tenant.pubsub;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Topics {

    public static final ChannelTopic TASK_CREATED = ChannelTopic.of("task.created");
    public static final ChannelTopic TASK_UPDATED = ChannelTopic.of("task.updated");

}
