package com.makar.tenant.task;

import com.makar.tenant.redis.RedisKeyTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskCommentAssociationRepositoryImpl implements TaskCommentAssociationRepository {

    private static final RedisKeyTemplate TASK_COMMENTS_ROOT = RedisKeyTemplate.root("task");

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void addCommentToTask(Long id, Long taskId) {
        var key = TASK_COMMENTS_ROOT.append(taskId, "comment").key();
        redisTemplate.opsForList().rightPush(key, id);
    }
}
