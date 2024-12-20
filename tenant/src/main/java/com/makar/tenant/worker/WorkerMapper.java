package com.makar.tenant.worker;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.makar.tenant.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.worker.rest.model.WorkerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public abstract class WorkerMapper {

    public abstract WorkerResponse toResponse(Worker worker);

    public abstract Worker toEntity(CreateWorkerRequest request) ;
}
