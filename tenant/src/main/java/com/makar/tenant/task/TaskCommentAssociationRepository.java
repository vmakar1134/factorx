package com.makar.tenant.task;

public interface TaskCommentAssociationRepository {

    void addCommentToTask(Long id, Long taskId);

}
