package com.bookstore.bsmanagement.exception;

/**
 * Exception thrown when a requested user is not found in the system.
 * This exception is typically thrown by service layer methods when attempting
 * to retrieve or operate on a user that does not exist.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     * 
     * @param message the detail message explaining why the user was not found
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with the specified detail message and cause.
     * 
     * @param message the detail message explaining why the user was not found
     * @param cause the cause of the exception
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserNotFoundException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
