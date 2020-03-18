package com.meghrajswami.virtex.exception;


/**
 * The base exception for the application. Thrown if there is an error during CRUD operations.
 *
 * @author Meghraj
 * @version 1.0
 */
public class VirtexException extends Exception {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -5752297664459863728L;

    /**
     * This is the constructor of <code>VirtexException</code> class with message argument.
     *
     * @param message the error message.
     */
    public VirtexException(String message) {
        super(message);
    }

    /**
     * This is the constructor of <code>VirtexException</code> class with message and cause arguments.
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public VirtexException(String message, Throwable cause) {
        super(message, cause);
    }
}


