package com.makar.tenant.notification;

import com.makar.tenant.pubsub.Message;
import com.makar.tenant.pubsub.Subscriber;
import com.makar.tenant.pubsub.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskCreatedSubscriber implements Subscriber {

    @Override
    public void handle(Message message) {
        log.info("Task created: {}", message);
    }

    @Override
    public ChannelTopic topic() {
        return Topics.TASK_CREATED;
    }

}
