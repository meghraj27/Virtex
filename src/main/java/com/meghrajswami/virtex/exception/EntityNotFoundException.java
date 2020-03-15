package com.meghrajswami.virtex.exception;

/**
 * This is exception is thrown if there is no entity with given id.
 *
 * @author Meghraj
 * @version 1.0
 */
public class EntityNotFoundException extends VirtexException {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 2384728348238423842L;

    /**
     * This is the constructor of <code>EntityNotFoundException</code> class with message argument.
     *
     * @param message the error message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * This is the constructor of <code>EntityNotFoundException</code> class with message and cause arguments.
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

