package com.makar.tenant.task.comment;

import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class TaskCommentMapper {

    public abstract TaskComment toEntity(TaskCommentRequest request);

}
