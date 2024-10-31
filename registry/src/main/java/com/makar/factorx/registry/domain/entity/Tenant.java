package com.makar.factorx.registry.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Tenant(
    @Id Long id,
    String schemaName
) {

}
