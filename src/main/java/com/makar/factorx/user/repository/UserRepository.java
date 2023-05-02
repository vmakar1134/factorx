package com.makar.factorx.user.repository;

import com.makar.factorx.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
