package com.makar.tenant.admin.rest;


import com.makar.tenant.admin.rest.model.AdminResponse;
import com.makar.tenant.admin.rest.model.CreateAdminRequest;
import com.makar.tenant.admin.rest.model.LoginAdminRequest;
import com.makar.tenant.admin.rest.model.RefreshRequest;
import com.makar.tenant.admin.rest.model.RegistrationRequest;
import com.makar.tenant.security.JwtTokenPair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    ResponseEntity<JwtTokenPair> login(@RequestBody LoginAdminRequest request);

    @PostMapping("/auth/refresh")
    ResponseEntity<JwtTokenPair> refresh(@RequestBody RefreshRequest request);

    @PostMapping("/auth/logout")
    ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/auth/logout/me")
    ResponseEntity<Void> logoutMe();

    @PostMapping("/auth/register")
    ResponseEntity<Void> register(@RequestBody RegistrationRequest request);

}
