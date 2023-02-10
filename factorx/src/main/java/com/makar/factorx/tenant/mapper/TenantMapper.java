package com.makar.factorx.tenant.mapper;

import com.makar.factorx.tenant.domain.Tenant;
import com.makar.factorx.tenant.rest.model.CreateTenantRequest;
import com.makar.factorx.tenant.rest.model.TenantResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TenantMapper {

    public abstract TenantResponse toResponse(Tenant entity);

    public abstract Tenant toEntity(CreateTenantRequest request);

}
