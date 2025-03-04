package com.makar.tenant.task;

import java.util.List;

public interface TaskCommentAssociationRepository {

    void addCommentToTask(Long commentId, Long taskId);

    void removeCommentFromTask(Long commentId, Long taskId);

    List<Long> findCommentIds(Long taskId);

}
