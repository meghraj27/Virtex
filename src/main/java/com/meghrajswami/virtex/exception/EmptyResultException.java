package com.meghrajswami.virtex.exception;

/**
 * This is exception is thrown if there is no entity with given id.
 *
 * @author Meghraj
 * @version 1.0
 */
public class EmptyResultException extends VirtexException {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = 2934783583584833287L;

    /**
     * This is the constructor of <code>EmptyResultException</code> class with message argument.
     *
     * @param message the error message.
     */
    public EmptyResultException(String message) {
        super(message);
    }

    /**
     * This is the constructor of <code>EmptyResultException</code> class with message and cause arguments.
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public EmptyResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
