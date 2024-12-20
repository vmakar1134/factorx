package com.makar.tenant.supervisor;


import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface SupervisorRepository extends ListCrudRepository<Supervisor, Long> {

    Optional<Supervisor> findByEmail(String email);
}
