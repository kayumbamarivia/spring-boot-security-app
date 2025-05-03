package com.me.security.doit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found.
 * Automatically returns a 404 NOT_FOUND status when thrown in a controller.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see ResponseStatus
 * @see RuntimeException
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String resourceName;
    private final String fieldName;
    private final transient Object fieldValue;
    
    /**
     * Constructs a new ResourceNotFoundException with resource, field, and value details.
     * 
     * @param resourceName the name of the resource that was not found
     * @param fieldName the name of the field that was searched
     * @param fieldValue the value that was searched for
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    /**
     * Constructs a new ResourceNotFoundException with resource and field details.
     * 
     * @param resourceName the name of the resource that was not found
     * @param fieldName the name of the field that was searched
     */
    public ResourceNotFoundException(String resourceName, String fieldName) {
        super(String.format("%s found with %s", resourceName, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = new Object();
    }

    /**
     * Gets the name of the resource that was not found.
     * 
     * @return the resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Gets the name of the field that was searched.
     * 
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Gets the value that was searched for.
     * 
     * @return the field value
     */
    public Object getFieldValue() {
        return fieldValue;
    }
}