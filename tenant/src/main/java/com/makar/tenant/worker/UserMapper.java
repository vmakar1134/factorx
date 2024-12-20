package com.makar.tenant.worker;

import com.makar.tenant.worker.rest.model.CreateUserRequest;
import com.makar.tenant.worker.rest.model.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserResponse toResponse(Worker worker);

    public abstract Worker toEntity(CreateUserRequest request) ;
}
