package com.makar.tenant.supervisor;

import com.makar.tenant.supervisor.rest.model.AdminResponse;
import com.makar.tenant.supervisor.rest.model.CreateAdminRequest;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class AdminMapper {

    public abstract Supervisor toEntity(CreateAdminRequest request);

    public abstract AdminResponse toResponse(Supervisor supervisor);

}
