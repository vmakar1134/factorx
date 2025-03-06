package com.makar.tenant.user.rest;

import com.makar.tenant.security.JwtTokenPair;
import com.makar.tenant.user.rest.model.LoginRequest;
import com.makar.tenant.user.rest.model.RegistrationRequest;
import com.makar.tenant.user.rest.model.RefreshRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("auth")
public interface AuthApi {

    @PostMapping("login")
    ResponseEntity<JwtTokenPair> login(@RequestBody LoginRequest request);

    @PostMapping("refresh")
    ResponseEntity<JwtTokenPair> refresh(@RequestBody RefreshRequest request);

    @PostMapping("logout")
    ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("logout/me")
    ResponseEntity<Void> logoutMe();

    @PostMapping("register")
    ResponseEntity<Void> register(@RequestBody RegistrationRequest request);

}
