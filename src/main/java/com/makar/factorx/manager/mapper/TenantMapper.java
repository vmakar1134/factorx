package com.makar.factorx.manager.mapper;

import com.makar.factorx.manager.domain.entity.Tenant;
import com.makar.factorx.manager.rest.model.CreateTenantRequest;
import com.makar.factorx.manager.rest.model.TenantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TenantMapper {

    @Mapping(target = "tenantName", source = "schemaName")
    public abstract TenantResponse toResponse(Tenant entity);

    @Mapping(target = "id", ignore = true)
    public abstract Tenant toEntity(CreateTenantRequest request);

}
