package com.makar.tenant.task.comment;

import com.makar.tenant.task.comment.rest.model.CommentRequest;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class TaskCommentMapper {

    public abstract TaskComment toEntity(CommentRequest request);

}
