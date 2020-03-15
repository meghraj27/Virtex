package com.meghrajswami.virtex.exception;

/**
 * This exception is thrown if a principal does not have access to a specific entity.
 *
 * @author Meghraj
 * @version 1.0
 */
public class AccessDeniedException extends VirtexException {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 2384835834957394758L;

    /**
     * This is the constructor of <code>AccessDeniedException</code> class with message argument.
     *
     * @param message the error message.
     */
    public AccessDeniedException(String message) {
        super(message);
    }

    /**
     * This is the constructor of <code>AccessDeniedException</code> class with message and cause arguments.
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}

