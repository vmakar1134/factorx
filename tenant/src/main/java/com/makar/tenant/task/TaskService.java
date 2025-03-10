package com.makar.tenant.task;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.task.rest.model.TaskRequest;
import com.makar.tenant.task.rest.model.TaskResponse;
import com.makar.tenant.user.worker.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final WorkerService workerService;

    private final TaskMapper taskMapper;

    public TaskResponse get(Long id) {
        var task = getById(id);
        return taskMapper.toResponse(task);
    }

    public Page<TaskResponse> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toResponse);
    }

    public void create(TaskRequest request) {
        validate(request);

        var task = taskMapper.toEntity(request);
        taskRepository.save(task);
    }

    private void validate(TaskRequest request) {
        if (request.workerId() != null && !workerService.exists(request.workerId())) {
            throw new IllegalArgumentException("Worker with id " + request.workerId() + " does not exist");
        }
    }

    public void update(Long id, TaskRequest request) {
        var task = taskMapper.update(getById(id), request);
        taskRepository.save(task);
    }

    private Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, "id", id));
    }

}
