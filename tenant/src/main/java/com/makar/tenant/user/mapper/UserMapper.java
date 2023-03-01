package com.makar.tenant.user.mapper;

import com.makar.tenant.user.entity.User;
import com.makar.tenant.user.rest.model.CreateUserRequest;
import com.makar.tenant.user.rest.model.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserResponse toResponse(User user);

    public abstract User toEntity(CreateUserRequest request) ;
}
