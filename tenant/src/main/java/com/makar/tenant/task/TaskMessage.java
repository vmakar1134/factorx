package com.makar.tenant.task;

import com.makar.tenant.broker.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskMessage extends Message {

    private final Long id;

    private final String title;

    private final String description;

    private final String status;

}
