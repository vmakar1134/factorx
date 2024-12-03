package com.makar.tenant.admin.mapper;

import com.makar.tenant.admin.entity.Admin;
import com.makar.tenant.admin.rest.model.AdminResponse;
import com.makar.tenant.admin.rest.model.CreateAdminRequest;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class AdminMapper {

    public abstract Admin toEntity(CreateAdminRequest request);

    public abstract AdminResponse toResponse(Admin admin);

}
