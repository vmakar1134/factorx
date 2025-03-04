package com.makar.tenant.task.comment;

import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import com.makar.tenant.task.comment.rest.model.UpdateTaskCommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class TaskCommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract TaskComment toEntity(TaskCommentRequest request);

    @Mapping(target = "content", source = "request.content")
    public abstract TaskComment update(TaskComment taskComment, UpdateTaskCommentRequest request);

}
