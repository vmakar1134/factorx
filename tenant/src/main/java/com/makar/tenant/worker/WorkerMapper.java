package com.makar.tenant.worker;

import com.makar.tenant.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.worker.rest.model.WorkerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class WorkerMapper {

    public abstract WorkerResponse toResponse(Worker worker);

    public abstract Worker toEntity(CreateWorkerRequest request) ;
}
