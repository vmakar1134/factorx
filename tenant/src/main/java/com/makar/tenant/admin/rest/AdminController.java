package com.makar.tenant.admin.rest;


import com.makar.tenant.admin.rest.model.AdminResponse;
import com.makar.tenant.admin.rest.model.CreateAdminRequest;
import com.makar.tenant.admin.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController  implements AdminApi {

    private final AdminService adminService;

    @Override
    public ResponseEntity<AdminResponse> getAdmin(Long id) {
        var body = adminService.get(id);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<AdminResponse>> getAdmins() {
        var body = adminService.get();
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> createAdmin(CreateAdminRequest request) {
        adminService.create(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAdmin(Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
