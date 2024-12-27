package com.makar.tenant.user.supervisor;

import com.makar.tenant.user.User;
import com.makar.tenant.user.supervisor.rest.model.CreateSupervisorRequest;
import com.makar.tenant.user.supervisor.rest.model.SupervisorResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class SupervisorMapper {

    public abstract User toEntity(CreateSupervisorRequest request);

    public abstract SupervisorResponse toResponse(User user);

}
