package com.meghrajswami.virtex.exception;

/**
 * The configuration exception for the application. Thrown if there is an error during in configuration.
 *
 * @author Meghraj
 * @version 1.0
 */
public class ConfigurationException extends RuntimeException {

    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -8237482342384782374L;

    /**
     * This is the constructor of <code>ConfigurationException</code> class with message argument.
     *
     * @param message the error message.
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * This is the constructor of <code>ConfigurationException</code> class with message and cause arguments.
     *
     * @param message the error message.
     * @param cause the cause of the exception.
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

