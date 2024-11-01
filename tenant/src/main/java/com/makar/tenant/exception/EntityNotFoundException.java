package com.makar.tenant.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, String field, Object value) {
        super(entityClass.getSimpleName() + " not found by " + field + ": " + value);
    }

}
