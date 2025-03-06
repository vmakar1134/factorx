package com.makar.tenant.user.admin.rest;

import com.makar.tenant.user.admin.AdminService;
import com.makar.tenant.user.admin.rest.model.AdminResponse;
import com.makar.tenant.user.admin.rest.model.CreateAdminRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

    private final AdminService adminService;

    @Override
    public ResponseEntity<AdminResponse> getAdmin(Long id) {
        var body = adminService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Page<AdminResponse>> getAdmins(Pageable pageable) {
        var body = adminService.getAll(pageable);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createAdmin(CreateAdminRequest request) {
        adminService.create(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteAdmin(Long id) {
        adminService.delete(id);
        return ResponseEntity.ok().build();
    }

}
