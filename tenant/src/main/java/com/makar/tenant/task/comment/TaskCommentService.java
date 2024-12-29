package com.makar.tenant.task.comment;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.task.Task;
import com.makar.tenant.task.TaskRepository;
import com.makar.tenant.task.comment.rest.model.TaskCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
