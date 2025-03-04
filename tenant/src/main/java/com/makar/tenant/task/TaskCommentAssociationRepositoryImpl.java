package com.makar.tenant.task;

import com.makar.tenant.redis.RedisKeyTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The name of repository should be kept in sync with the name of the interface.
 * It's because of the Spring Data naming convention that is used to find the repository implementation.
 */
@Repository
@RequiredArgsConstructor
public class TaskCommentAssociationRepositoryImpl implements TaskCommentAssociationRepository {

    private static final RedisKeyTemplate TASK_COMMENTS_ROOT = RedisKeyTemplate.root("task");
    private static final String COMMENT_PATH = "comment";

    private final RedisTemplate<String, Long> redisTemplate;

    @Override
    public void addCommentToTask(Long commentId, Long taskId) {
        var key = taskKey(taskId);
        redisTemplate.opsForList().rightPush(key, commentId);
    }

    @Override
    public void removeCommentFromTask(Long commentId, Long taskId) {
        var key = taskKey(taskId);
        redisTemplate.opsForList().remove(key, 1, commentId);
    }

    @Override
    public List<Long> findCommentIds(Long taskId) {
        return redisTemplate.opsForList().range(taskKey(taskId), 0, -1);
    }

    private String taskKey(Long taskId) {
        return TASK_COMMENTS_ROOT.append(taskId, COMMENT_PATH).key();
    }
}
