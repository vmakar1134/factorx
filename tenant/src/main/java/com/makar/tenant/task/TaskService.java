package com.makar.tenant.task;

import com.makar.tenant.pubsub.Publisher;
import com.makar.tenant.pubsub.Topics;
import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.task.rest.model.TaskRequest;
import com.makar.tenant.task.rest.model.TaskResponse;
import com.makar.tenant.worker.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final WorkerService workerService;

    private final TaskMapper taskMapper;

    private final Publisher publisher;

    public TaskResponse get(Long id) {
        var task = getById(id);
        return taskMapper.toResponse(task);
    }

    public void create(TaskRequest request) {
        if (!workerService.exists(request.workerId())) {
            throw new IllegalArgumentException("Worker with id " + request.workerId() + " does not exist");
        }

        var task = taskMapper.toEntity(request);
        taskRepository.save(task);
        publisher.publish(Topics.TASK_CREATED, taskMapper.toMessage(task));
    }

    public void update(Long id, TaskRequest request) {
        var task = taskMapper.update(getById(id), request);
        taskRepository.save(task);
        publisher.publish(Topics.TASK_UPDATED, taskMapper.toMessage(task));
    }

    private Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, "id", id));
    }

}
