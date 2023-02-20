package com.makar.factorx.registry.repository;

import com.makar.factorx.registry.domain.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
