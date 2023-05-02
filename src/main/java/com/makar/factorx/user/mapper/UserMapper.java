package com.makar.factorx.user.mapper;

import com.makar.factorx.user.entity.User;
import com.makar.factorx.user.rest.model.CreateUserRequest;
import com.makar.factorx.user.rest.model.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserResponse toResponse(User user);

    public abstract User toEntity(CreateUserRequest request);
}
