package com.makar.tenant.task;

import com.makar.tenant.security.IdentityProvider;
import com.makar.tenant.task.rest.model.TaskRequest;
import com.makar.tenant.task.rest.model.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, imports = IdentityProvider.class)
public abstract class TaskMapper {

    public abstract TaskResponse toResponse(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "supervisorId", expression = "java(IdentityProvider.currentId())")
    public abstract Task toEntity(TaskRequest request);

    public abstract TaskMessage toMessage(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "supervisorId", ignore = true)
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "workerId", source = "request.workerId")
    @Mapping(target = "deadline", source = "request.deadline")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "priority", source = "request.priority")
    public abstract Task update(Task task, TaskRequest request);

}
