package com.makar.tenant.admin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record Admin(
    @Id
    Long id,

    String email
) {

}
