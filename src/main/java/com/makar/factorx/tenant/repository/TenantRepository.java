package com.makar.factorx.tenant.repository;

import com.makar.factorx.tenant.domain.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
