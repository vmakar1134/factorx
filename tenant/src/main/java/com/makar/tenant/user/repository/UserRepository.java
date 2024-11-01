package com.makar.tenant.user.repository;

import com.makar.tenant.user.entity.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {

}
