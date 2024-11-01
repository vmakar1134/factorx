package com.makar.tenant.user.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public record User(
    @Id
    Long id,

    String username
) {

}
