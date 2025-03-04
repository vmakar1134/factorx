package com.makar.tenant.task;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends ListCrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

}
