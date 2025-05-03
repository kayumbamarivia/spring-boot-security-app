package com.me.security.doit.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Custom exception for API errors with associated HTTP status.
 * Used to return consistent error responses from the API.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see RuntimeException
 */
public class UserAPIException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final HttpStatus status;
    private final String message;

    /**
     * Constructs a new UserAPIException with status and message.
     * 
     * @param status the HTTP status code
     * @param message the error message
     */
    public UserAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructs a new UserAPIException with message, status, and detail message.
     * 
     * @param message the detail message
     * @param status the HTTP status code
     * @param message1 the error message
     */
    public UserAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    /**
     * Gets the HTTP status code.
     * 
     * @return the HTTP status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Gets the error message.
     * 
     * @return the error message
     */
    @Override
    public String getMessage() {
        return message;
    }
}