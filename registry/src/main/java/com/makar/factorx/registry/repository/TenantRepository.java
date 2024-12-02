package com.makar.factorx.registry.repository;

import com.makar.factorx.registry.domain.entity.Tenant;
import org.springframework.data.repository.ListCrudRepository;

public interface TenantRepository extends ListCrudRepository<Tenant, Long> {

}
