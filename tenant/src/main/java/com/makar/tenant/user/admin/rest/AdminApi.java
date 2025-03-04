package com.makar.tenant.user.admin.rest;

import java.util.List;

import com.makar.tenant.user.admin.rest.model.CreateSupervisorRequest;
import com.makar.tenant.user.admin.rest.model.SupervisorResponse;
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
    ResponseEntity<SupervisorResponse> getAdmin(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<SupervisorResponse>> getAdmins();

    @PostMapping
    ResponseEntity<Void> createAdmin(@RequestBody CreateSupervisorRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteAdmin(@PathVariable("id") Long id);

}
