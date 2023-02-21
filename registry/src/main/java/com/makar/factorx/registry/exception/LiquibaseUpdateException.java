package com.makar.factorx.registry.exception;

public class LiquibaseUpdateException extends RuntimeException{

    public LiquibaseUpdateException(String message) {
        super(message);
    }

    public LiquibaseUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

}
