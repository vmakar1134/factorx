package com.makar.tenant.task.comment;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.task.Task;
import com.makar.tenant.task.TaskRepository;
import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import com.makar.tenant.task.comment.rest.model.UpdateTaskCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;

    private final TaskCommentMapper taskCommentMapper;

    private final TaskRepository taskRepository;

    public void create(TaskCommentRequest request) {
        if (!taskRepository.existsById(request.taskId())) {
            throw new EntityNotFoundException(Task.class, "id", "Task not found");
        }

        var taskComment = taskCommentMapper.toEntity(request);
        var id = taskCommentRepository.save(taskComment).id();
        taskCommentRepository.addCommentToTask(id, request.taskId());
    }

    public void delete(Long id) {
        var comment = get(id);
        taskCommentRepository.removeCommentFromTask(id, comment.taskId());
        taskCommentRepository.deleteById(id);
    }

    public void update(Long id, UpdateTaskCommentRequest request) {
        var taskComment = get(id);
        var task = taskCommentMapper.update(taskComment, request);
        taskCommentRepository.save(task);
    }

    public List<TaskComment> getComments(Long taskId) {
        var commentsIds = taskCommentRepository.findCommentIds(taskId);
        return taskCommentRepository.findAllById(commentsIds);
    }

    private TaskComment get(Long id) {
        return taskCommentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TaskComment.class, "id", "Task comment not found"));
    }

}
