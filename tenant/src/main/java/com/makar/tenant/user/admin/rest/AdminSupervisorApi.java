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

@RequestMapping("admins/supervisors")
public interface AdminSupervisorApi {

    @GetMapping("{id}")
    ResponseEntity<SupervisorResponse> getSupervisor(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<SupervisorResponse>> getSupervisors();

    @PostMapping
    ResponseEntity<Void> createSupervisor(@RequestBody CreateSupervisorRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteSupervisor(@PathVariable("id") Long id);

}
