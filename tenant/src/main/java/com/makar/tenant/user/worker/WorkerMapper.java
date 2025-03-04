package com.makar.tenant.user.worker;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.makar.tenant.user.User;
import com.makar.tenant.user.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.user.worker.rest.model.WorkerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public abstract class WorkerMapper {

    public abstract WorkerResponse toResponse(User worker);

    public abstract User toEntity(CreateWorkerRequest request) ;
}
