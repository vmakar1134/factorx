package com.makar.factorx.manager.repository;

import com.makar.factorx.manager.domain.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
