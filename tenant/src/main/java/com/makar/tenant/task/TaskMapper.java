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

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "supervisorId", expression = "java(IdentityProvider.currentId())")
    public abstract Task toEntity(TaskRequest request);
}
