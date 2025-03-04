package com.makar.tenant.user.worker.rest;

import com.makar.tenant.user.worker.rest.model.CreateWorkerRequest;
import com.makar.tenant.user.worker.rest.model.WorkerResponse;
import com.makar.tenant.user.worker.service.WorkerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<WorkerResponse>> getUsers() {
        var body = workerService.get();
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
