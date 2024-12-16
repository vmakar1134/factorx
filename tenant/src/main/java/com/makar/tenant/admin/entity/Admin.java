package com.makar.tenant.admin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table
public record Admin(
        @Id
        Long id,
        String password,
        String otp,
        Instant otpCreatedAt,
        String email
) {
}
