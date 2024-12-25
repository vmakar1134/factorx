package com.makar.tenant.broker;

public interface Subscriber {

    void handle(Message message);

}
