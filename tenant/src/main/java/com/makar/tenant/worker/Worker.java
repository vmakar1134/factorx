package com.makar.tenant.worker;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Worker(
        @Id
        Long id,
        String password,
        String username
) {
}
