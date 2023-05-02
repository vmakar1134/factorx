package com.makar.factorx.admin.mapper;



import com.makar.factorx.admin.entity.Admin;
import com.makar.factorx.admin.rest.model.AdminResponse;
import com.makar.factorx.admin.rest.model.CreateAdminRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AdminMapper {

    public abstract Admin toEntity(CreateAdminRequest request);

    public abstract AdminResponse toResponse(Admin admin);

}
