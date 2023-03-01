package com.makar.tenant.user.repository;

import com.makar.tenant.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
