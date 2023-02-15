package com.makar.factorx.admin.repository;

import com.makar.factorx.admin.domain.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
