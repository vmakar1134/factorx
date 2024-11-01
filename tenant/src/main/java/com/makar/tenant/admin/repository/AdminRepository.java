package com.makar.tenant.admin.repository;


import com.makar.tenant.admin.entity.Admin;
import org.springframework.data.repository.ListCrudRepository;

public interface AdminRepository extends ListCrudRepository<Admin, Long> {

}
