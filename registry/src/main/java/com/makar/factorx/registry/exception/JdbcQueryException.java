package com.makar.factorx.registry.exception;

public class JdbcQueryException extends RuntimeException{

    public JdbcQueryException(String message) {
        super(message);
    }

    public JdbcQueryException(String message, Throwable cause) {
        super(message, cause);
    }

}
