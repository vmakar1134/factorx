package com.makar.tenant.user.worker.service;

import com.makar.tenant.exception.EntityNotFoundException;
import com.makar.tenant.user.User;
import com.makar.tenant.user.UserRepository;
import com.makar.tenant.user.worker.WorkerMapper;
import com.makar.tenant.user.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.user.worker.rest.model.WorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final UserRepository workerRepository;

    private final WorkerMapper userMapper;

    public WorkerResponse get(Long id) {
        var worker = getById(id);
        return userMapper.toResponse(worker);
    }

    public boolean exists(Long id) {
        return workerRepository.existsById(id);
    }

    public Page<WorkerResponse> get(Pageable pageable) {
        return workerRepository.findAll(pageable)
            .map(userMapper::toResponse);
    }

    public void create(CreateWorkerRequest request) {
        var user = userMapper.toEntity(request);
        workerRepository.save(user);
    }

    public void delete(Long id) {
        workerRepository.deleteById(id);
    }

    private User getById(Long id) {
        return workerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id));
    }

}
