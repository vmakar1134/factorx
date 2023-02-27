package com.makar.tenant.mapper;



import com.makar.tenant.entity.Admin;
import com.makar.tenant.rest.model.AdminResponse;
import com.makar.tenant.rest.model.CreateAdminRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AdminMapper {

    public abstract Admin toEntity(CreateAdminRequest request);

    public abstract AdminResponse toResponse(Admin admin);

}
