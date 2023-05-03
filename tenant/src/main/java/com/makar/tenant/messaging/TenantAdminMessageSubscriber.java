package com.makar.tenant.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TenantAdminMessageSubscriber implements MessageListener {

    public void onMessage(Message message, byte[] pattern) {
        log.info("Message received: ");
        log.info(new String(message.getBody()));
    }

}
