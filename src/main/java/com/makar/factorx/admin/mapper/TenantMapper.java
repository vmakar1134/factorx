package com.makar.factorx.admin.mapper;

import com.makar.factorx.admin.domain.entity.Tenant;
import com.makar.factorx.admin.rest.model.CreateTenantRequest;
import com.makar.factorx.admin.rest.model.TenantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TenantMapper {

    @Mapping(target = "tenantName", source = "schemaName")
    public abstract TenantResponse toResponse(Tenant entity);

    @Mapping(target = "id", ignore = true)
    public abstract Tenant toEntity(CreateTenantRequest request);

}
