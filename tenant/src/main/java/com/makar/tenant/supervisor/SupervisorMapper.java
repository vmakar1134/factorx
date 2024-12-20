package com.makar.tenant.supervisor;

import com.makar.tenant.supervisor.rest.model.CreateSupervisorRequest;
import com.makar.tenant.supervisor.rest.model.SupervisorResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class SupervisorMapper {

    public abstract Supervisor toEntity(CreateSupervisorRequest request);

    public abstract SupervisorResponse toResponse(Supervisor supervisor);

}
