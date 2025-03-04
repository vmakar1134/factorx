package com.makar.tenant.user;


import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends ListCrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
