package com.makar.tenant.user;

import com.makar.tenant.security.UserRole;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record User(
        @Id
        Long id,
        String password,
        String otp,
        Instant otpCreatedAt,
        String email,
        UserRole role
) {
}
