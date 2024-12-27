package com.makar.tenant.user;

import com.makar.tenant.security.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserPrincipalMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "credentials.username", source = "email")
    @Mapping(target = "credentials.password", source = "password")
    @Mapping(target = "authorities", ignore = true)
    public abstract UserPrincipal toPrincipal(User user);

}
