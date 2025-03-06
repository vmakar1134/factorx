package com.makar.tenant.user.admin.rest;

import com.makar.tenant.user.admin.rest.model.AdminResponse;
import com.makar.tenant.user.admin.rest.model.CreateAdminRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    ResponseEntity<Page<AdminResponse>> getAdmins(@ParameterObject @PageableDefault Pageable pageable);

    @PostMapping
    ResponseEntity<Void> createAdmin(@RequestBody CreateAdminRequest request);

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteAdmin(@PathVariable("id") Long id);

}
