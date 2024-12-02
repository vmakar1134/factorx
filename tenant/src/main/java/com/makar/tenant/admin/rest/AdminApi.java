package com.makar.tenant.admin.rest;


import com.makar.tenant.admin.rest.model.AdminResponse;
import com.makar.tenant.admin.rest.model.CreateAdminRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("admins")
public interface AdminApi {

    @GetMapping("{id}")
    ResponseEntity<AdminResponse> getAdmin(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<AdminResponse>> getAdmins();

    @PostMapping
    ResponseEntity<Void> createAdmin(@RequestBody CreateAdminRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteAdmin(@PathVariable("id") Long id);

    @PostMapping("/auth/login")
    ResponseEntity<Void> login();

}
