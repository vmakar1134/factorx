package com.makar.tenant.worker.service;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.worker.Worker;
import com.makar.tenant.worker.WorkerMapper;
import com.makar.tenant.worker.WorkerRepository;
import com.makar.tenant.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.worker.rest.model.WorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;

    private final WorkerMapper userMapper;

    public WorkerResponse get(Long id) {
        Worker worker = getById(id);
        return userMapper.toResponse(worker);
    }

    public List<WorkerResponse> get() {
        return workerRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public void create(CreateWorkerRequest request) {
        var user = userMapper.toEntity(request);
        workerRepository.save(user);
    }

    public void delete(Long id) {
        workerRepository.deleteById(id);
    }

    private Worker getById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Worker.class, "id", id));
    }

}
