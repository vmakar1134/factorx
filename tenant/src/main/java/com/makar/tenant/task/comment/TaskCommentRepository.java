package com.makar.tenant.task.comment;

import com.makar.tenant.redis.RedisKeyTemplate;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCommentRepository implements ListCrudRepository<TaskComment, Long> {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final RedisKeyTemplate TASK_COMMENTS_ROOT = RedisKeyTemplate.root("task", "comments");
    private static final RedisKeyTemplate TASK_COMMENT_ID_ROOT = RedisKeyTemplate.root("task", "comment", "id");
    private static final RedisKeyTemplate COMMENTS_ROOT = RedisKeyTemplate.root("comments");

    private void saveComment(TaskComment taskComment) {
        redisTemplate.opsForValue().set(key(COMMENTS_ROOT, taskComment.id()), taskComment);
    }

    private void addTaskComment(Long id) {
        redisTemplate.opsForList().rightPush(key(TASK_COMMENTS_ROOT, id), id);
    }

    private Long incrementTaskCommentId() {
        return redisTemplate.opsForValue().increment(TASK_COMMENT_ID_ROOT.key());
    }

    private TaskComment copyWithId(TaskComment taskComment, Long id) {
        return new TaskComment(id,
                taskComment.parentId(),
                taskComment.taskId(),
                taskComment.authorId(),
                taskComment.content(),
                taskComment.createdAt(),
                taskComment.updatedAt());
    }

    // TODO: investigave the need of transaction here.
    @Override
    @SuppressWarnings("unchecked")
    public <S extends TaskComment> S save(@Nonnull S entity) {
        var id = incrementTaskCommentId();
        var saved = copyWithId(entity, id);
        saveComment(saved);
        addTaskComment(id);
        log.info("Task comment saved: {}", id);
        return (S) saved;
    }

    @Override
    public <S extends TaskComment> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<TaskComment> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<TaskComment> findAll() {
        return List.of();
    }

    @Override
    public List<TaskComment> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(TaskComment entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends TaskComment> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private String key(RedisKeyTemplate template, Long id) {
        return template.path(String.valueOf(id));
    }

}
