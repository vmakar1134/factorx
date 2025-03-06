package com.makar.tenant.user.admin;

import com.makar.tenant.user.User;

import com.makar.tenant.user.admin.rest.model.AdminResponse;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.makar.tenant.user.admin.rest.model.CreateAdminRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface AdminMapper {

    AdminResponse toResponse(User user);

    User toEntity(CreateAdminRequest request);

}
