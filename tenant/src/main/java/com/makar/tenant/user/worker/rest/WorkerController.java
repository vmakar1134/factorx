package com.makar.tenant.user.worker.rest;

import com.makar.tenant.user.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.user.worker.rest.model.WorkerResponse;
import com.makar.tenant.user.worker.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkerController implements WorkerApi {

    private final WorkerService workerService;

    @Override
    public ResponseEntity<WorkerResponse> getUser(Long id) {
        var body = workerService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Page<WorkerResponse>> getUsers(Pageable pageable) {
        var body = workerService.get(pageable);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createUser(CreateWorkerRequest request) {
        workerService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        workerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
