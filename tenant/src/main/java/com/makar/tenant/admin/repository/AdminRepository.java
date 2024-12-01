package com.makar.tenant.admin.repository;


import com.makar.tenant.admin.entity.Admin;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface AdminRepository extends ListCrudRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
}
