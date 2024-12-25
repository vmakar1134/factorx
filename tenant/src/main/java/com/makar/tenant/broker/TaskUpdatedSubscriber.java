package com.makar.tenant.broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskUpdatedSubscriber implements Subscriber {

    @Override
    public void handle(Message message) {
        log.info("Task updated: {}", message);
    }

}
