package com.makar.tenant.task.comment;

import com.makar.tenant.task.TaskCommentAssociationRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface TaskCommentRepository extends ListCrudRepository<TaskComment, Long>, TaskCommentAssociationRepository {

}
