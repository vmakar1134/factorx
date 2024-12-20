package com.makar.tenant.worker;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface WorkerRepository extends ListCrudRepository<Worker, Long> {

    Optional<Worker> findByUsername(String username);
    
}
